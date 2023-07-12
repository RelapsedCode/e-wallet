package gen.drazhev.ewallet.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gen.drazhev.ewallet.model.PlatformFunds;
import gen.drazhev.ewallet.repository.PlatformFundsRepositoryInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/platform")
public class PlatformController {

	private final PlatformFundsRepositoryInterface platformFundsRepositoryInterface;

	//	@Autowired - dont kill unit tests
	public PlatformController(PlatformFundsRepositoryInterface platformFundsRepositoryInterface) {
		this.platformFundsRepositoryInterface = platformFundsRepositoryInterface;
	}

/*	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(path = "/add")
	public Wallet create(@RequestParam String address, @RequestParam double balance) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		Wallet wallet = new Wallet();
		wallet.setAddress(address);
		System.out.println();
		wallet.setBalance(balance);

		log.info("Wallet address: " + wallet.getAddress());
		log.info("Wallet balance: " + wallet.getBalance() + "\n");
		return wallet;
	}*/

	@PostMapping(path = "/getFunds")
	public PlatformFunds getFunds(@RequestParam String address, @RequestParam double balance) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		Optional<PlatformFunds> platformFunds = platformFundsRepositoryInterface.findById(1);

		log.info("Platform funds last fee: " + platformFunds.get().getLastAddedFee());
		log.info("Platform funds total gross amount: " + platformFunds.get().getTotalGrossAmount());
		return platformFunds.get();
	}

}
