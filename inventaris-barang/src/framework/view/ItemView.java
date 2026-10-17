package framework.view;

import adapter.presenter.ItemPresenter;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import framework.util.InputUtil;
import usecase.ItemUseCase;

/**
 * Tampilan konsol aplikasi inventaris barang.
 * Bertugas menerima input, memanggil use case, dan menangani exception domain.
 * Tidak berisi logika bisnis maupun format output (diserahkan ke presenter).
 */
public class ItemView {

    private static final String QUANTITY_FIELD = "Jumlah stok";

    private final ItemUseCase itemUseCase;
    private final ItemPresenter presenter;

    public ItemView(ItemUseCase itemUseCase, ItemPresenter presenter) {
        this.itemUseCase = itemUseCase;
        this.presenter = presenter;
    }

    /** Menjalankan loop menu utama sampai pengguna memilih keluar. */
    public void show() {
        boolean running = true;
        while (running) {
            presenter.showItems(itemUseCase.getAllItems());
            presenter.showMenu();

            String choice = InputUtil.input("Pilih");
            presenter.showBlankLine();

            switch (choice) {
                case "1" -> addItem();
                case "2" -> updateStock();
                case "3" -> searchItem();
                case "4" -> sortItem();
                case "5" -> removeItem();
                case "x" -> running = false;
                default -> presenter.showInvalidChoice();
            }

            if (running) {
                presenter.showBlankLine();
            }
        }
    }

    private void addItem() {
        presenter.showSectionTitle("Menambah Barang");

        String name = InputUtil.input("Nama (x Jika Batal)");
        if (InputUtil.isCancel(name)) {
            return;
        }
        String rawQuantity = InputUtil.input("Jumlah");
        if (InputUtil.isCancel(rawQuantity)) {
            return;
        }

        int quantity;
        try {
            quantity = InputUtil.parseInt(rawQuantity, QUANTITY_FIELD);
        } catch (ValidationException e) {
            presenter.showError(e.getMessage());
            return;
        }

        String category = InputUtil.input("Kategori (x Jika Batal)");
        if (InputUtil.isCancel(category)) {
            return;
        }

        try {
            presenter.showAddSuccess(itemUseCase.addItem(name, quantity, category));
        } catch (ValidationException e) {
            presenter.showError(e.getMessage());
        }
    }

    private void updateStock() {
        presenter.showSectionTitle("Mengubah Stok");

        String rawId = InputUtil.input("ID Barang yang diubah (x Jika Batal)");
        if (InputUtil.isCancel(rawId)) {
            return;
        }

        int id;
        try {
            id = InputUtil.parseInt(rawId, "ID");
        } catch (ValidationException e) {
            presenter.showError(e.getMessage());
            return;
        }

        String rawQuantity = InputUtil.input("Jumlah Baru (Kosongkan jika tidak ingin mengubah)");

try {
    // Input kosong = stok tidak diubah (null)
    Integer quantity = rawQuantity.isBlank()
            ? null
            : InputUtil.parseInt(rawQuantity, QUANTITY_FIELD);
    itemUseCase.updateStock(id, quantity);
    presenter.showUpdateStockSuccess();
} catch (ValidationException e) {
            presenter.showError(e.getMessage());
        } catch (EntityNotFoundException e) {
            presenter.showUpdateFailed(e.getId());
        }
    }

    private void searchItem() {
        presenter.showSectionTitle("Mencari Barang");

        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");
        if (InputUtil.isCancel(keyword)) {
            return;
        }

        presenter.showSearchResults(itemUseCase.searchItems(keyword), keyword);
    }

    private void sortItem() {
        presenter.showSectionTitle("Mengurutkan Barang");
        presenter.showSortMenu();

        String input = InputUtil.input("Pilih");
        presenter.showBlankLine();
        if (InputUtil.isCancel(input)) {
            return;
        }

        try {
            SortOption option = InputUtil.parseChoice(input, SortOption.values(), "Pilihan");
            presenter.showSortedItems(itemUseCase.sortItems(option));
        } catch (ValidationException e) {
            presenter.showError(e.getMessage());
        }
    }

    private void removeItem() {
        presenter.showSectionTitle("Menghapus Barang");

        String rawId = InputUtil.input("[ID Barang] yang dihapus (x Jika Batal)");
        if (InputUtil.isCancel(rawId)) {
            return;
        }

        try {
            itemUseCase.removeItem(InputUtil.parseInt(rawId, "ID"));
            presenter.showRemoveSuccess();
        } catch (ValidationException e) {
            presenter.showError(e.getMessage());
        } catch (EntityNotFoundException e) {
            presenter.showRemoveFailed(e.getId());
        }
    }
}
