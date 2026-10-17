package domain.repository;

import domain.entity.Contact;

/**
 * Port repository khusus kontak.
 * Menambahkan pembuatan kontak baru dengan ID otomatis yang ditentukan oleh repository.
 */
public interface IContactRepository extends IRepository<Contact, Integer> {

    /**
     * Membuat dan menyimpan kontak baru.
     *
     * @return kontak yang tersimpan, lengkap dengan ID
     */
    Contact save(String name, String phone, String email);
}
