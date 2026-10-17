package usecase;

import domain.entity.SortOption;
import domain.entity.Transaction;
import domain.entity.TransactionType;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.ITransactionRepository;
import java.util.List;

/**
 * Logika bisnis pencatatan keuangan.
 * Tidak melakukan I/O; kegagalan dilaporkan lewat checked exception domain.
 */
public class FinanceUseCase {

    private static final String ENTITY_NAME = "Transaksi";

    private final ITransactionRepository transactionRepository;

    public FinanceUseCase(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    /**
     * Menambah transaksi baru.
     *
     * @throws ValidationException jika keterangan kosong atau jumlah tidak lebih dari 0
     */
    public Transaction addTransaction(String description, long amount, TransactionType type)
            throws ValidationException {
        if (description == null || description.isBlank()) {
            throw new ValidationException("Keterangan tidak boleh kosong");
        }
        if (amount <= 0) {
            throw new ValidationException("Jumlah tidak valid");
        }
        return transactionRepository.save(description.trim(), amount, type);
    }

    /** @throws EntityNotFoundException jika ID tidak ditemukan */
    public void removeTransaction(int id) throws EntityNotFoundException {
        if (!transactionRepository.deleteById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
    }

    /** Mencari transaksi berdasarkan keterangan (tidak membedakan huruf besar/kecil). */
    public List<Transaction> searchTransactions(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return transactionRepository.findBy(transaction -> transaction.getDescription().toLowerCase().contains(lowerKeyword));
    }

    public List<Transaction> sortTransactions(SortOption option) {
        return transactionRepository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }

    /** Saldo = total pemasukan - total pengeluaran. */
    public long getBalance() {
        return transactionRepository.findAll().stream()
                .mapToLong(Transaction::signedAmount)
                .sum();
    }
}
