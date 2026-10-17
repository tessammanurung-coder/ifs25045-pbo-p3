package domain.repository;

import domain.entity.Activity;

/**
 * Port repository khusus kegiatan.
 * Menambahkan pembuatan kegiatan baru dengan ID otomatis yang ditentukan oleh repository.
 */
public interface IActivityRepository extends IRepository<Activity, Integer> {

    /**
     * Membuat dan menyimpan kegiatan baru.
     *
     * @return kegiatan yang tersimpan, lengkap dengan ID
     */
    Activity save(String title, String day, String time);
}
