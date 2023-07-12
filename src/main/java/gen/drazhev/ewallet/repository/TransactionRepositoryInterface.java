package gen.drazhev.ewallet.repository;

import org.springframework.data.repository.CrudRepository;

import gen.drazhev.ewallet.model.Transaction;
import gen.drazhev.ewallet.model.Wallet;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
//Spring automatically implements this repository interface in a bean that has the same name
public interface TransactionRepositoryInterface extends CrudRepository<Transaction, Integer> {


}
