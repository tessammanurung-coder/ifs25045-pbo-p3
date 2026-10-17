package domain.entity;

/**
 * Entity inti yang merepresentasikan satu transaksi keuangan.
 * Bersifat immutable: seluruh field ditetapkan saat transaksi dibuat.
 * Tidak mengetahui cara tampil maupun cara penyimpanan.
 */
public class Transaction {

    private final int id;
    private final String description;
    private final long amount;
    private final TransactionType type;

    public Transaction(int id, String description, long amount, TransactionType type) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public long getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    /** Pengaruh transaksi terhadap saldo: positif untuk pemasukan, negatif untuk pengeluaran. */
    public long signedAmount() {
        return type == TransactionType.INCOME ? amount : -amount;
    }
}
