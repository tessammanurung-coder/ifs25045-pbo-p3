package domain.entity;

/**
 * Entity inti yang merepresentasikan satu barang di inventaris.
 * ID tidak dapat diubah; perubahan stok dilakukan lewat method bernama jelas.
 */
public class Item {

    private final int id;
    private final String name;
    private int quantity;
    private final String category;

    public Item(int id, String name, int quantity, String category) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    /** Mengubah jumlah stok barang. */
    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }
}
