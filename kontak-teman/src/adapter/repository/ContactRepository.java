package adapter.repository;

import domain.entity.Contact;
import domain.repository.IContactRepository;

/**
 * Implementasi in-memory {@link IContactRepository} berbasis {@link InMemoryRepository}.
 */
public class ContactRepository extends InMemoryRepository<Contact, Integer> implements IContactRepository {

    private int lastId = 0;

    public ContactRepository() {
        super(Contact::getId);
    }

    @Override
    public Contact save(String name, String phone, String email) {
        return save(new Contact(++lastId, name, phone, email));
    }
}
