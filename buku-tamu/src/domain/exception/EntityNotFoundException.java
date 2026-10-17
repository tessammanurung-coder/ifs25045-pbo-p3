package domain.exception;

/**
 * Dilempar ketika entitas dengan ID tertentu tidak ditemukan di repository.
 */
public class EntityNotFoundException extends DomainException {

    private static final long serialVersionUID = 1L;

    private final int id;

    /**
     * @param entityName nama entitas untuk pesan error (mis. "Transaksi")
     * @param id         ID yang dicari namun tidak ditemukan
     */
    public EntityNotFoundException(String entityName, int id) {
        super(entityName + " dengan ID " + id + " tidak ditemukan");
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
