package domain.repository;

import domain.entity.Item;

/**
 * Port repository khusus barang.
 * Menambahkan pembuatan barang baru dengan ID otomatis yang ditentukan oleh repository.
 */
public interface IItemRepository extends IRepository<Item, Integer> {

    /**
     * Membuat dan menyimpan barang baru.
     *
     * @return barang yang tersimpan, lengkap dengan ID
     */
    Item save(String name, int quantity, String category);
}
