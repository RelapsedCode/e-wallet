package gen.drazhev.ewallet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import gen.drazhev.ewallet.helpers.ConfigFileReader;
import gen.drazhev.ewallet.model.Transaction;
import gen.drazhev.ewallet.model.Wallet;
import gen.drazhev.ewallet.repository.PlatformFundsRepositoryInterface;
import gen.drazhev.ewallet.repository.TransactionRepositoryInterface;
import gen.drazhev.ewallet.repository.WalletRepositoryInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/transaction")
public class TransactionController {

	private WalletRepositoryInterface walletRepositoryInterface;
	private TransactionRepositoryInterface transactionRepositoryInterface;
	private PlatformFundsRepositoryInterface platformFundsRepositoryInterface;

	public TransactionController(WalletRepositoryInterface walletRepositoryInterface, TransactionRepositoryInterface transactionRepositoryInterface,
			PlatformFundsRepositoryInterface platformFundsRepositoryInterface) {
		this.walletRepositoryInterface = walletRepositoryInterface;
		this.transactionRepositoryInterface = transactionRepositoryInterface;
		this.platformFundsRepositoryInterface = platformFundsRepositoryInterface;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(path = "/create")
	public Transaction createTransaction(@RequestParam String transactionUUID, @RequestParam double amount, @RequestParam String senderAddress,
			@RequestParam String receiverAddress) {
		Transaction transaction = new Transaction();
		transaction.setTransactionUUID(transactionUUID);
		transaction.setAmount(amount);
		transaction.setSenderAddress(senderAddress);
		transaction.setReceiverAddress(receiverAddress);
		transaction.setStatus("PENDING");
		processTransaction(transaction);

		log.info("Transaction details: \n"
				+ "UUID: " + transaction.getTransactionUUID() + "\n"
				+ "Amount: " + transaction.getAmount() + "\n"
				+ "Sender Address: " + transaction.getSenderAddress() + "\n"
				+ "Receiver Address: " + transaction.getReceiverAddress() + "\n"
				+ "Status: " + transaction.getStatus() + "\n");
		return transaction;
	}

	public Transaction processTransaction(Transaction trans) {
		List<Transaction> transactionList = List.of(trans);
		Wallet senderWallet = walletRepositoryInterface.findByAddressIgnoreCase(trans.getSenderAddress());
		Wallet receiverWallet = walletRepositoryInterface.findByAddressIgnoreCase(trans.getReceiverAddress());

		double fee = (Math.round(
				(trans.getAmount() * Double.parseDouble(new ConfigFileReader().getPropertyValue("exchange-platform-fee")) / 100) * 100.0) / 100.0);
		double amountToBeSubtracted = trans.getAmount();
		double amountToBeAdded = trans.getAmount() - fee;
		log.info("Fee: " + fee);
		log.info("Amount to be subtracted: " + amountToBeSubtracted);
		log.info("Amount to be added: " + amountToBeAdded);

		if (senderWallet.getBalance() < amountToBeSubtracted) {
			trans.setStatus("REJECTED");
			throw new RuntimeException("Balance is not sufficient");
		} else {
			senderWallet.setBalance(Math.round((senderWallet.getBalance() - amountToBeSubtracted) * 100.0) / 100.0);
			log.info("Sender new balance: " + senderWallet.getBalance());
			receiverWallet.setBalance(Math.round((receiverWallet.getBalance() + amountToBeAdded) * 100.0) / 100.0);
			log.info("Receiver new balance: " + receiverWallet.getBalance());

			senderWallet.setLastTransaction(transactionList);
			receiverWallet.setLastTransaction(transactionList);
			trans.setStatus("APPROVED");
		}
		transactionRepositoryInterface.save(trans);
		new PlatformController(platformFundsRepositoryInterface)
				.SetPlatformFunds(fee);
		return trans;
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(path = "/all")
	public @ResponseBody
	Iterable<Transaction> GetAllTransactions() {
		return transactionRepositoryInterface.findAll();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(path = "/findtransaction")
	public @ResponseBody
	Transaction FindTransaction(@RequestParam String transactionUUID) {
		return transactionRepositoryInterface.findByTransactionUUID(transactionUUID);
	}
}
