package gen.drazhev.ewallet.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;

import gen.drazhev.ewallet.model.PlatformFunds;
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
	// create a transaction
	// check the balance of the sender wallet
	// subtract the money, add the money to the new acc
	// set transaction status

	private WalletRepositoryInterface walletRepositoryInterface;
	private TransactionRepositoryInterface transactionRepositoryInterface;
	private PlatformFundsRepositoryInterface platformFundsRepositoryInterface;

	Faker faker = new Faker();

	//	@Autowired
	public TransactionController(WalletRepositoryInterface walletRepositoryInterface, TransactionRepositoryInterface transactionRepositoryInterface,
			PlatformFundsRepositoryInterface platformFundsRepositoryInterface) {
		this.walletRepositoryInterface = walletRepositoryInterface;
		this.transactionRepositoryInterface = transactionRepositoryInterface;
		this.platformFundsRepositoryInterface = platformFundsRepositoryInterface;
	}

	public void createTransaction(String transactionUUID, double amount, String senderAddress, String receiverAddress, String status) {
		Transaction transaction = new Transaction();
		transaction.setTransactionUUID(transactionUUID);
		transaction.setAmount(amount);
		transaction.setSenderAddress(senderAddress);
		transaction.setReceiverAddress(receiverAddress);
		transaction.setStatus(status);
		processTransaction(transaction);

		log.info("Transaction details: \n"
				+ "UUID: " + transaction.getTransactionUUID() + "\n"
				+ "Amount: " + transaction.getAmount() + "\n"
				+ "Sender Address: " + transaction.getSenderAddress() + "\n"
				+ "Receiver Address: " + transaction.getReceiverAddress() + "\n"
				+ "Status: " + transaction.getStatus() + "\n");
	}

	public void processTransaction(Transaction trans) {
		List<Transaction> transactionList = List.of(trans);
		Wallet senderWallet = walletRepositoryInterface.findByAddressIgnoreCase(trans.getSenderAddress());
		Wallet receiverWallet = walletRepositoryInterface.findByAddressIgnoreCase(trans.getReceiverAddress());

		double fee = (trans.getAmount() * 1) / 100;
		double amountToBeSubtracted = trans.getAmount();
		double amountToBeAdded = trans.getAmount() - fee;
		log.info("Fee: " + fee);
		log.info("Amount to be subtracted: " + amountToBeSubtracted);
		log.info("Amount to be added: " + amountToBeAdded);

		if (senderWallet.getBalance() < amountToBeSubtracted) {
			trans.setStatus("REJECTED");
			throw new RuntimeException("Balance is not sufficient");
		} else {
			senderWallet.setBalance(senderWallet.getBalance() - amountToBeSubtracted);
			log.info("Sender new balance: " + senderWallet.getBalance());
			receiverWallet.setBalance(receiverWallet.getBalance() + amountToBeAdded);
			log.info("Receiver new balance: " + receiverWallet.getBalance());

			senderWallet.setLastTransaction(transactionList);
			receiverWallet.setLastTransaction(transactionList);
			trans.setStatus("APPROVED");
		}
		transactionRepositoryInterface.save(trans);
		setPlatformFunds(fee);
	}

	public void setPlatformFunds(double fee) {
		double currentTotalComp = 0;
		PlatformFunds platformFunds;
		Optional<PlatformFunds> platformFundsDB = platformFundsRepositoryInterface.findById(1);
		if (platformFundsDB.isEmpty()) {
			platformFunds = new PlatformFunds();
		} else {
			platformFunds = platformFundsDB.get();
		}
		currentTotalComp = platformFunds.getTotalGrossAmount() + fee;
		platformFunds.setTotalGrossAmount(currentTotalComp);
		platformFunds.setLastAddedFee(fee);

		platformFundsRepositoryInterface.save(platformFunds);
	}

}
