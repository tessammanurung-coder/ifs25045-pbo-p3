package domain.repository;

import domain.entity.Guest;

/**
 * Port repository khusus tamu.
 * Menambahkan pembuatan tamu baru dengan ID otomatis yang ditentukan oleh repository.
 */
public interface IGuestRepository extends IRepository<Guest, Integer> {

    /**
     * Membuat dan menyimpan tamu baru.
     *
     * @return tamu yang tersimpan, lengkap dengan ID
     */
    Guest save(String name, String purpose);
}
