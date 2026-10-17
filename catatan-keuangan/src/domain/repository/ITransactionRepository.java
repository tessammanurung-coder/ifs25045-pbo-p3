package domain.repository;

import domain.entity.Transaction;
import domain.entity.TransactionType;

/**
 * Port repository khusus transaksi.
 * Menambahkan pembuatan transaksi baru dengan ID otomatis yang ditentukan oleh repository.
 */
public interface ITransactionRepository extends IRepository<Transaction, Integer> {

    /**
     * Membuat dan menyimpan transaksi baru.
     *
     * @return transaksi yang tersimpan, lengkap dengan ID
     */
    Transaction save(String description, long amount, TransactionType type);
}
