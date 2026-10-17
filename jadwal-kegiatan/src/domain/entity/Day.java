package domain.entity;

/**
 * Hari dalam seminggu menurut penamaan Indonesia.
 * Dipakai untuk menentukan urutan hari (Senin-Minggu) saat pengurutan jadwal.
 */
public enum Day {
    SENIN("Senin"),
    SELASA("Selasa"),
    RABU("Rabu"),
    KAMIS("Kamis"),
    JUMAT("Jumat"),
    SABTU("Sabtu"),
    MINGGU("Minggu");

    private final String label;

    Day(String label) {
        this.label = label;
    }

    /**
     * Menentukan urutan sebuah nama hari dalam seminggu (tidak membedakan huruf besar/kecil).
     * Nama yang tidak dikenal mendapat urutan terakhir sehingga tidak mengganggu pengurutan.
     */
    public static int rankOf(String dayName) {
        for (Day day : values()) {
            if (day.label.equalsIgnoreCase(dayName.trim())) {
                return day.ordinal();
            }
        }
        return values().length;
    }
}
