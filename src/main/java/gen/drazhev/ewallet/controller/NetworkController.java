package gen.drazhev.ewallet.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;

import gen.drazhev.ewallet.model.Transaction;
import gen.drazhev.ewallet.model.Wallet;
import gen.drazhev.ewallet.repository.PlatformFundsRepositoryInterface;
import gen.drazhev.ewallet.repository.TransactionRepositoryInterface;
import gen.drazhev.ewallet.repository.WalletRepositoryInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/network")
public class NetworkController {

	private WalletRepositoryInterface walletRepositoryInterface;
	private TransactionRepositoryInterface transactionRepositoryInterface;
	private PlatformFundsRepositoryInterface platformFundsRepositoryInterface;

	private Transaction transaction;
	private TransactionController transactionController;
	private WalletController walletController;

	Faker faker = new Faker();

	public NetworkController(WalletRepositoryInterface walletRepositoryInterface, TransactionRepositoryInterface transactionRepositoryInterface,
			PlatformFundsRepositoryInterface platformFundsRepositoryInterface) {
		this.walletRepositoryInterface = walletRepositoryInterface;
		this.transactionRepositoryInterface = transactionRepositoryInterface;
		this.platformFundsRepositoryInterface = platformFundsRepositoryInterface;
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping(path = "/magic")
	public void generateRandomTransaction(String senderAddress, String receiverAddress, Double amount) {
		transactionController = new TransactionController(walletRepositoryInterface, transactionRepositoryInterface, platformFundsRepositoryInterface);
		String walletSender = Optional.ofNullable(senderAddress).orElseGet(() ->
				createRandomWallet(faker.number().randomDouble(2, 50000, 150000)));
		String walletReceiver = Optional.ofNullable(receiverAddress).orElseGet(() ->
				createRandomWallet(faker.number().randomDouble(2, 10000, 50000)));
		Double amountToSend = Optional.ofNullable(amount).orElseGet(() ->
				faker.number().randomDouble(2, 10, 20000));
		transactionController.createTransaction(faker.internet().uuid(), amountToSend, walletSender, walletReceiver, "PENDING");
	}

	public String createRandomWallet(double balance) {
		walletController = new WalletController(walletRepositoryInterface);
		Wallet wallet = walletController.create(faker.internet().uuid(), balance);
		return walletRepositoryInterface.findByAddressIgnoreCase(wallet.getAddress()).getAddress();
	}

}
