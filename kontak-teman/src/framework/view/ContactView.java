package framework.view;

import adapter.presenter.ContactPresenter;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import framework.util.InputUtil;
import usecase.ContactUseCase;

/**
 * Tampilan konsol aplikasi kontak teman.
 * Bertugas menerima input, memanggil use case, dan menangani exception domain.
 * Tidak berisi logika bisnis maupun format output (diserahkan ke presenter).
 */
public class ContactView {

    private final ContactUseCase contactUseCase;
    private final ContactPresenter presenter;

    public ContactView(ContactUseCase contactUseCase, ContactPresenter presenter) {
        this.contactUseCase = contactUseCase;
        this.presenter = presenter;
    }

    /** Menjalankan loop menu utama sampai pengguna memilih keluar. */
    public void show() {
        boolean running = true;
        while (running) {
            presenter.showContacts(contactUseCase.getAllContacts());
            presenter.showMenu();

            String choice = InputUtil.input("Pilih");
            presenter.showBlankLine();

            switch (choice) {
                case "1" -> addContact();
                case "2" -> updateContact();
                case "3" -> searchContact();
                case "4" -> sortContact();
                case "5" -> removeContact();
                case "x" -> running = false;
                default -> presenter.showInvalidChoice();
            }

            if (running) {
                presenter.showBlankLine();
            }
        }
    }

    private void addContact() {
        presenter.showSectionTitle("Menambah Kontak");

        String name = InputUtil.input("Nama (x Jika Batal)");
        if (InputUtil.isCancel(name)) {
            return;
        }
        String phone = InputUtil.input("Telepon");
        if (InputUtil.isCancel(phone)) {
            return;
        }
        String email = InputUtil.input("Email");
        if (InputUtil.isCancel(email)) {
            return;
        }

        try {
            presenter.showAddSuccess(contactUseCase.addContact(name, phone, email));
        } catch (ValidationException e) {
            presenter.showError(e.getMessage());
        }
    }

    private void updateContact() {
        presenter.showSectionTitle("Mengubah Kontak");

        String rawId = InputUtil.input("ID Kontak yang diubah (x Jika Batal)");
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

        // Input kosong berarti field tidak diubah (dikirim ke use case sebagai null).
        String name = blankToNull(InputUtil.input("Nama Baru (Kosongkan jika tidak ingin mengubah)"));
        String phone = blankToNull(InputUtil.input("Telepon Baru (Kosongkan jika tidak ingin mengubah)"));
        String email = blankToNull(InputUtil.input("Email Baru (Kosongkan jika tidak ingin mengubah)"));

        try {
            contactUseCase.updateContact(id, name, phone, email);
            presenter.showUpdateSuccess();
        } catch (ValidationException e) {
            presenter.showError(e.getMessage());
        } catch (EntityNotFoundException e) {
            presenter.showUpdateFailed(e.getId());
        }
    }

    private void searchContact() {
        presenter.showSectionTitle("Mencari Kontak");

        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");
        if (InputUtil.isCancel(keyword)) {
            return;
        }

        presenter.showSearchResults(contactUseCase.searchContacts(keyword), keyword);
    }

    private void sortContact() {
        presenter.showSectionTitle("Mengurutkan Kontak");
        presenter.showSortMenu();

        String input = InputUtil.input("Pilih");
        presenter.showBlankLine();
        if (InputUtil.isCancel(input)) {
            return;
        }

        try {
            SortOption option = InputUtil.parseChoice(input, SortOption.values(), "Pilihan");
            presenter.showSortedContacts(contactUseCase.sortContacts(option));
        } catch (ValidationException e) {
            presenter.showError(e.getMessage());
        }
    }

    private void removeContact() {
        presenter.showSectionTitle("Menghapus Kontak");

        String rawId = InputUtil.input("[ID Kontak] yang dihapus (x Jika Batal)");
        if (InputUtil.isCancel(rawId)) {
            return;
        }

        try {
            contactUseCase.removeContact(InputUtil.parseInt(rawId, "ID"));
            presenter.showRemoveSuccess();
        } catch (ValidationException e) {
            presenter.showError(e.getMessage());
        } catch (EntityNotFoundException e) {
            presenter.showRemoveFailed(e.getId());
        }
    }

    private String blankToNull(String value) {
        return value.isBlank() ? null : value;
    }
}
