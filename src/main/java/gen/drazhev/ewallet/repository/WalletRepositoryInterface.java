package gen.drazhev.ewallet.repository;

import org.springframework.data.repository.CrudRepository;

import gen.drazhev.ewallet.model.Wallet;

public interface WalletRepositoryInterface extends CrudRepository<Wallet, Integer> {

	Wallet findByAddressIgnoreCase(String address);

}
