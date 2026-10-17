package domain.exception;

/**
 * Dilempar ketika input atau aturan bisnis tidak terpenuhi
 * (misalnya field kosong, angka tidak valid, atau format salah).
 * Pesan exception ditulis tanpa tanda baca akhir agar presenter yang menentukan format tampilnya.
 */
public class ValidationException extends DomainException {

    private static final long serialVersionUID = 1L;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
