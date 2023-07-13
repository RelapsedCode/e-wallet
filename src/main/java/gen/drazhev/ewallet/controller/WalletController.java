package gen.drazhev.ewallet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import gen.drazhev.ewallet.model.Wallet;
import gen.drazhev.ewallet.repository.WalletRepositoryInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/wallet")
public class WalletController {

	private final WalletRepositoryInterface walletRepositoryInterface;

	public WalletController(WalletRepositoryInterface walletRepositoryInterface) {
		this.walletRepositoryInterface = walletRepositoryInterface;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(path = "/create")
	public Wallet Create(@RequestParam String address, @RequestParam double balance) {
		Wallet wallet = new Wallet();
		wallet.setAddress(address);
		System.out.println();
		wallet.setBalance(balance);
		walletRepositoryInterface.save(wallet);
		log.info("Wallet address: " + wallet.getAddress());
		log.info("Wallet balance: " + wallet.getBalance() + "\n");
		return wallet;
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(path = "/address")
	public @ResponseBody
	Wallet GetAllWallets(@RequestParam String walletAddress) {
		return walletRepositoryInterface.findByAddressIgnoreCase(walletAddress);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(path = "/balance")
	public @ResponseBody
	String GetWalletBalance(@RequestParam String walletAddress) {
		return String.valueOf(walletRepositoryInterface.findByAddressIgnoreCase(walletAddress).getBalance());
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(path = "/all")
	public @ResponseBody
	Iterable<Wallet> GetAllWallets() {
		return walletRepositoryInterface.findAll();
	}

}
