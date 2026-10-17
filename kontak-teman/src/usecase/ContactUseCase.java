package usecase;

import domain.entity.Contact;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IContactRepository;
import java.util.List;

/**
 * Logika bisnis kontak teman.
 * Tidak melakukan I/O; kegagalan dilaporkan lewat checked exception domain.
 */
public class ContactUseCase {

    private static final String ENTITY_NAME = "Kontak";

    private final IContactRepository contactRepository;

    public ContactUseCase(IContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    /**
     * Menambah kontak baru.
     *
     * @throws ValidationException jika nama, telepon, atau email kosong
     */
    public Contact addContact(String name, String phone, String email) throws ValidationException {
        validateName(name);
        validatePhone(phone);
        validateEmail(email);
        return contactRepository.save(name.trim(), phone.trim(), email.trim());
    }

    /**
     * Mengubah kontak secara parsial: parameter {@code null} berarti field tidak diubah.
     * Seluruh input divalidasi terlebih dahulu sehingga kontak tidak pernah terubah sebagian.
     *
     * @throws EntityNotFoundException jika ID tidak ditemukan
     * @throws ValidationException     jika salah satu field yang diubah tidak valid
     */
    public void updateContact(int id, String name, String phone, String email)
            throws EntityNotFoundException, ValidationException {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME, id));

        if (name != null) {
            validateName(name);
        }
        if (phone != null) {
            validatePhone(phone);
        }
        if (email != null) {
            validateEmail(email);
        }

        if (name != null) {
            contact.changeName(name.trim());
        }
        if (phone != null) {
            contact.changePhone(phone.trim());
        }
        if (email != null) {
            contact.changeEmail(email.trim());
        }
        contactRepository.update(contact);
    }

    /** @throws EntityNotFoundException jika ID tidak ditemukan */
    public void removeContact(int id) throws EntityNotFoundException {
        if (!contactRepository.deleteById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
    }

    /** Mencari kontak berdasarkan nama (tidak membedakan huruf besar/kecil). */
    public List<Contact> searchContacts(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return contactRepository.findBy(contact -> contact.getName().toLowerCase().contains(lowerKeyword));
    }

    public List<Contact> sortContacts(SortOption option) {
        return contactRepository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }

    private void validateName(String name) throws ValidationException {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Nama tidak boleh kosong");
        }
    }

    private void validatePhone(String phone) throws ValidationException {
        if (phone == null || phone.isBlank()) {
            throw new ValidationException("Nomor telepon tidak boleh kosong");
        }
    }

    private void validateEmail(String email) throws ValidationException {
        if (email == null || email.isBlank()) {
            throw new ValidationException("Email tidak boleh kosong");
        }
    }
}
