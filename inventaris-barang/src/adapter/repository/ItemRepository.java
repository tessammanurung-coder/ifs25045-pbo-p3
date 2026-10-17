package adapter.repository;

import domain.entity.Item;
import domain.repository.IItemRepository;

/**
 * Implementasi in-memory {@link IItemRepository} berbasis {@link InMemoryRepository}.
 */
public class ItemRepository extends InMemoryRepository<Item, Integer> implements IItemRepository {

    private int lastId = 0;

    public ItemRepository() {
        super(Item::getId);
    }

    @Override
    public Item save(String name, int quantity, String category) {
        return save(new Item(++lastId, name, quantity, category));
    }
}
