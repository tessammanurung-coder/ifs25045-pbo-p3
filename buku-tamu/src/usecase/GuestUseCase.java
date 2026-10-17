package usecase;

import domain.entity.Guest;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IGuestRepository;
import java.util.List;

/**
 * Logika bisnis buku tamu.
 * Tidak melakukan I/O; kegagalan dilaporkan lewat checked exception domain.
 */
public class GuestUseCase {

    private static final String ENTITY_NAME = "Tamu";

    private final IGuestRepository guestRepository;

    public GuestUseCase(IGuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    /**
     * Mendaftarkan tamu baru.
     *
     * @throws ValidationException jika nama atau tujuan kunjungan kosong
     */
    public Guest registerGuest(String name, String purpose) throws ValidationException {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Nama tidak boleh kosong");
        }
        if (purpose == null || purpose.isBlank()) {
            throw new ValidationException("Tujuan kunjungan tidak boleh kosong");
        }
        return guestRepository.save(name.trim(), purpose.trim());
    }

    /** @throws EntityNotFoundException jika ID tidak ditemukan */
    public void removeGuest(int id) throws EntityNotFoundException {
        if (!guestRepository.deleteById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
    }

    /** Mencari tamu berdasarkan nama (tidak membedakan huruf besar/kecil). */
    public List<Guest> searchGuests(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return guestRepository.findBy(guest -> guest.getName().toLowerCase().contains(lowerKeyword));
    }
}
