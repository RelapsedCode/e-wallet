package gen.drazhev.ewallet.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import gen.drazhev.ewallet.model.PlatformFunds;
import gen.drazhev.ewallet.repository.PlatformFundsRepositoryInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/platform")
public class PlatformController {

	private final PlatformFundsRepositoryInterface platformFundsRepositoryInterface;

	public PlatformController(PlatformFundsRepositoryInterface platformFundsRepositoryInterface) {
		this.platformFundsRepositoryInterface = platformFundsRepositoryInterface;
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping(path = "/setfunds")
	public PlatformFunds SetPlatformFunds(@RequestParam double fee) {
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
		return platformFunds;
	}

	@GetMapping(path = "/getfunds")
	public @ResponseBody
	PlatformFunds GetFunds() {
		Optional<PlatformFunds> platformFunds = platformFundsRepositoryInterface.findById(1);
		log.info("Platform funds last fee: " + platformFunds.get().getLastAddedFee());
		log.info("Platform funds total gross amount: " + platformFunds.get().getTotalGrossAmount());
		return platformFunds.get();
	}

}
