package adapter.repository;

import domain.entity.Transaction;
import domain.entity.TransactionType;
import domain.repository.ITransactionRepository;

/**
 * Implementasi in-memory {@link ITransactionRepository} berbasis {@link InMemoryRepository}.
 */
public class TransactionRepository extends InMemoryRepository<Transaction, Integer>
        implements ITransactionRepository {

    private int lastId = 0;

    public TransactionRepository() {
        super(Transaction::getId);
    }

    @Override
    public Transaction save(String description, long amount, TransactionType type) {
        return save(new Transaction(++lastId, description, amount, type));
    }
}
