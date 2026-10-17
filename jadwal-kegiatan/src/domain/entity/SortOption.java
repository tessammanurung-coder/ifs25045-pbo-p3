package domain.entity;

import java.util.Comparator;

/**
 * Kriteria pengurutan kegiatan.
 * Setiap opsi membawa comparator-nya sendiri sehingga aturan pengurutan terpusat di domain.
 * Urutan deklarasi sama dengan urutan nomor pada menu pengurutan.
 */
public enum SortOption {
    /** Urut hari (Senin-Minggu); pada hari yang sama diurutkan berdasarkan waktu. */
    DAY(Comparator.comparingInt((Activity activity) -> Day.rankOf(activity.getDay()))
            .thenComparing(Activity::getTime)),

    /** Urut waktu terawal; pada waktu yang sama diurutkan berdasarkan hari. */
    TIME(Comparator.comparing(Activity::getTime)
            .thenComparingInt(activity -> Day.rankOf(activity.getDay()))),

    /** Judul dari A ke Z (tidak membedakan huruf besar/kecil). */
    TITLE_ASC(Comparator.comparing(Activity::getTitle, String.CASE_INSENSITIVE_ORDER)),

    /** Judul dari Z ke A (tidak membedakan huruf besar/kecil). */
    TITLE_DESC(Comparator.comparing(Activity::getTitle, String.CASE_INSENSITIVE_ORDER).reversed());

    private final Comparator<Activity> comparator;

    SortOption(Comparator<Activity> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Activity> comparator() {
        return comparator;
    }
}
