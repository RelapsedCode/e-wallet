package gen.drazhev.ewallet.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;

import gen.drazhev.ewallet.model.PlatformFunds;
import gen.drazhev.ewallet.model.Transaction;
import gen.drazhev.ewallet.model.Wallet;
import gen.drazhev.ewallet.repository.PlatformFundsRepositoryInterface;
import gen.drazhev.ewallet.repository.TransactionRepositoryInterface;
import gen.drazhev.ewallet.repository.WalletRepositoryInterface;

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

	@Autowired
	public TransactionController(WalletRepositoryInterface walletRepositoryInterface, TransactionRepositoryInterface transactionRepositoryInterface,
			PlatformFundsRepositoryInterface platformFundsRepositoryInterface) {
		this.walletRepositoryInterface = walletRepositoryInterface;
		this.transactionRepositoryInterface = transactionRepositoryInterface;
		this.platformFundsRepositoryInterface = platformFundsRepositoryInterface;
	}

	public void createTransaction() {

	}

	public void processTransaction(Transaction trans) {
		List<Transaction> transactionList = List.of(trans);
		Wallet senderWallet = walletRepositoryInterface.findByAddressIgnoreCase(trans.getSenderAddress());
		Wallet receiverWallet = walletRepositoryInterface.findByAddressIgnoreCase(trans.getReceiverAddress());

		double fee = (trans.getAmount() * 1) / 100;
		double amountToBeSubtracted = trans.getAmount();
		double amountToBeAdded = trans.getAmount() - fee;

		if (senderWallet.getBalance() < amountToBeSubtracted) {
			trans.setStatus("REJECTED");
			throw new RuntimeException("Balance is not sufficient");
		} else {
			senderWallet.setBalance(senderWallet.getBalance() - amountToBeSubtracted);
			receiverWallet.setBalance(receiverWallet.getBalance() + amountToBeAdded);

			//			List<Transaction> allTransactions = Stream.concat(transactionList.stream(), senderWallet.getTransactions().stream()).toList();
			senderWallet.setLastTransaction(transactionList);
			receiverWallet.setLastTransaction(transactionList);
			trans.setStatus("APPROVED");

		}
		transactionRepositoryInterface.save(trans);
	}

	public void setPlatformFunds(double fee) {

		Optional.ofNullable(senderAddress).orElseGet(() ->
				createWallet(faker.number().randomDouble(2, 10000, 50000)));

		Optional<PlatformFunds> platformFunds = platformFundsRepositoryInterface.findById(1);
		platformFunds.isPresent()


		platformFundsRepositoryInterface.findById(1);
		PlatformFunds platformFunds = new PlatformFunds();
		platformFunds.setLastAddedFee(fee);
	}

}
