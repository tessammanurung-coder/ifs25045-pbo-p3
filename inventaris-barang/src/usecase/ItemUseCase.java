package usecase;

import domain.entity.Item;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IItemRepository;
import java.util.List;

/**
 * Logika bisnis inventaris barang.
 * Tidak melakukan I/O; kegagalan dilaporkan lewat checked exception domain.
 */
public class ItemUseCase {

    private static final String ENTITY_NAME = "Barang";

    private final IItemRepository itemRepository;

    public ItemUseCase(IItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    /**
     * Menambah barang baru.
     *
     * @throws ValidationException jika nama/kategori kosong atau stok tidak lebih dari 0
     */
    public Item addItem(String name, int quantity, String category) throws ValidationException {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Nama barang tidak boleh kosong");
        }
        validateQuantity(quantity);
        if (category == null || category.isBlank()) {
            throw new ValidationException("Kategori tidak boleh kosong");
        }
        return itemRepository.save(name.trim(), quantity, category.trim());
    }

    /**
     * Mengubah jumlah stok barang.
     *
     * @throws EntityNotFoundException jika ID tidak ditemukan
     * @throws ValidationException     jika stok tidak lebih dari 0
     */
    public void updateStock(int id, Integer quantity) throws EntityNotFoundException, ValidationException {
    Item item = itemRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME, id));

    if (quantity != null) {
        validateQuantity(quantity);
        item.changeQuantity(quantity);
    }
    itemRepository.update(item);
}

    /** @throws EntityNotFoundException jika ID tidak ditemukan */
    public void removeItem(int id) throws EntityNotFoundException {
        if (!itemRepository.deleteById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
    }

    /** Mencari barang berdasarkan nama (tidak membedakan huruf besar/kecil). */
    public List<Item> searchItems(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return itemRepository.findBy(item -> item.getName().toLowerCase().contains(lowerKeyword));
    }

    public List<Item> sortItems(SortOption option) {
        return itemRepository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }

    private void validateQuantity(int quantity) throws ValidationException {
        if (quantity <= 0) {
            throw new ValidationException("Jumlah stok tidak valid");
        }
    }
}
