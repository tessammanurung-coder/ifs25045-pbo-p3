package domain.exception;

/**
 * Checked exception dasar untuk seluruh hierarki kegagalan di layer domain.
 * Dipakai sebagai tipe umum agar pemanggil bisa menangani semua kegagalan domain sekaligus.
 */
public class DomainException extends Exception {

    private static final long serialVersionUID = 1L;

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
