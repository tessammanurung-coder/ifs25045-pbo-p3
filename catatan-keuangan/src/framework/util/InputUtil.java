package framework.util;

import domain.exception.ValidationException;
import java.util.Scanner;

/**
 * Utility pembacaan dan konversi input keyboard.
 * Berada di layer framework karena bergantung langsung pada {@link System#in}.
 */
public final class InputUtil {

    /** Kata kunci yang dipakai pengguna untuk membatalkan suatu operasi. */
    private static final String CANCEL_KEYWORD = "x";

    private static final Scanner SCANNER = new Scanner(System.in);

    private InputUtil() {
    }

    /**
     * Menampilkan prompt lalu membaca satu baris input.
     * Jika input sudah habis (EOF), dikembalikan kata kunci batal agar aplikasi berhenti dengan aman.
     *
     * @param info label yang ditampilkan sebelum input
     */
    public static String input(String info) {
        System.out.print(info + " : ");
        return SCANNER.hasNextLine() ? SCANNER.nextLine() : CANCEL_KEYWORD;
    }

    /** Menentukan apakah input pengguna berarti membatalkan operasi. */
    public static boolean isCancel(String value) {
        return CANCEL_KEYWORD.equals(value);
    }

    /**
     * Mengonversi teks menjadi int.
     *
     * @param fieldName nama field untuk pesan error (mis. "ID")
     * @throws ValidationException jika teks bukan angka bulat yang valid
     */
    public static int parseInt(String value, String fieldName) throws ValidationException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ValidationException(fieldName + " tidak valid", e);
        }
    }

    /**
     * Mengonversi teks menjadi long.
     *
     * @param fieldName nama field untuk pesan error (mis. "Jumlah")
     * @throws ValidationException jika teks bukan angka bulat yang valid
     */
    public static long parseLong(String value, String fieldName) throws ValidationException {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new ValidationException(fieldName + " tidak valid", e);
        }
    }

    /**
     * Memilih satu opsi dari daftar berdasarkan nomor menu (dimulai dari 1).
     *
     * @param options   daftar opsi sesuai urutan tampil di menu
     * @param fieldName nama field untuk pesan error (mis. "Pilihan")
     * @throws ValidationException jika bukan angka atau di luar rentang menu
     */
    public static <T> T parseChoice(String value, T[] options, String fieldName) throws ValidationException {
        int number = parseInt(value, fieldName);
        if (number < 1 || number > options.length) {
            throw new ValidationException(fieldName + " tidak valid");
        }
        return options[number - 1];
    }
}
