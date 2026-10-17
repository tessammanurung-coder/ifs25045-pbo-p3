package domain.entity;

import java.util.Comparator;

/**
 * Kriteria pengurutan barang.
 * Setiap opsi membawa comparator-nya sendiri sehingga aturan pengurutan terpusat di domain.
 * Urutan deklarasi sama dengan urutan nomor pada menu pengurutan.
 */
public enum SortOption {
    /** Nama dari A ke Z (tidak membedakan huruf besar/kecil). */
    NAME_ASC(Comparator.comparing(Item::getName, String.CASE_INSENSITIVE_ORDER)),

    /** Nama dari Z ke A (tidak membedakan huruf besar/kecil). */
    NAME_DESC(Comparator.comparing(Item::getName, String.CASE_INSENSITIVE_ORDER).reversed()),

    /** Stok terkecil lebih dulu. */
    QUANTITY_ASC(Comparator.comparingInt(Item::getQuantity)),

    /** Stok terbesar lebih dulu. */
    QUANTITY_DESC(Comparator.comparingInt(Item::getQuantity).reversed());

    private final Comparator<Item> comparator;

    SortOption(Comparator<Item> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Item> comparator() {
        return comparator;
    }
}
