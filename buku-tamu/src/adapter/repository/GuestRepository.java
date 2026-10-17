package adapter.repository;

import domain.entity.Guest;
import domain.repository.IGuestRepository;

/**
 * Implementasi in-memory {@link IGuestRepository} berbasis {@link InMemoryRepository}.
 */
public class GuestRepository extends InMemoryRepository<Guest, Integer> implements IGuestRepository {

    private int lastId = 0;

    public GuestRepository() {
        super(Guest::getId);
    }

    @Override
    public Guest save(String name, String purpose) {
        return save(new Guest(++lastId, name, purpose));
    }
}
