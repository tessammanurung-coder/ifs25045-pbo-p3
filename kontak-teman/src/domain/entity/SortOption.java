package domain.entity;

import java.util.Comparator;

/**
 * Kriteria pengurutan kontak.
 * Setiap opsi membawa comparator-nya sendiri sehingga aturan pengurutan terpusat di domain.
 * Urutan deklarasi sama dengan urutan nomor pada menu pengurutan.
 */
public enum SortOption {
    /** Nama dari A ke Z (tidak membedakan huruf besar/kecil). */
    NAME_ASC(Comparator.comparing(Contact::getName, String.CASE_INSENSITIVE_ORDER)),

    /** Nama dari Z ke A (tidak membedakan huruf besar/kecil). */
    NAME_DESC(Comparator.comparing(Contact::getName, String.CASE_INSENSITIVE_ORDER).reversed());

    private final Comparator<Contact> comparator;

    SortOption(Comparator<Contact> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Contact> comparator() {
        return comparator;
    }
}
