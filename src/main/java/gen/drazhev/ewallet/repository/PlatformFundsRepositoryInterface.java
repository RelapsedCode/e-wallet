package gen.drazhev.ewallet.repository;

import org.springframework.data.repository.CrudRepository;

import gen.drazhev.ewallet.model.PlatformFunds;
import gen.drazhev.ewallet.model.Transaction;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
//Spring automatically implements this repository interface in a bean that has the same name
public interface PlatformFundsRepositoryInterface extends CrudRepository<PlatformFunds, Integer> {


}
