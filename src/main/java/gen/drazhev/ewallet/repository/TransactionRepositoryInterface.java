package gen.drazhev.ewallet.repository;

import org.springframework.data.repository.CrudRepository;

import gen.drazhev.ewallet.model.Transaction;

public interface TransactionRepositoryInterface extends CrudRepository<Transaction, Integer> {

	Transaction findByTransactionUUID(String transactionUUID);
}
