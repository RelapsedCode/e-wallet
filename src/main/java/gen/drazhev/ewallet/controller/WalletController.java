package gen.drazhev.ewallet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import gen.drazhev.ewallet.model.Wallet;
import gen.drazhev.ewallet.repository.WalletRepositoryInterface;

@RestController
@RequestMapping("/wallet")
public class WalletController {

	private final WalletRepositoryInterface walletRepositoryInterface;

	//	@Autowired - dont kill unit tests
	public WalletController(WalletRepositoryInterface walletRepositoryInterface) {
		this.walletRepositoryInterface = walletRepositoryInterface;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(path = "/create")
	public Wallet create(@RequestParam String address, @RequestParam double balance) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		Wallet wallet = new Wallet();
		wallet.setAddress(address);
		wallet.setBalance(balance);
		walletRepositoryInterface.save(wallet);
		return wallet;
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping(path = "/all")
	public @ResponseBody Iterable<Wallet> getAllWallets() {
		// This returns a JSON or XML with the users
		return walletRepositoryInterface.findAll();
	}


}
