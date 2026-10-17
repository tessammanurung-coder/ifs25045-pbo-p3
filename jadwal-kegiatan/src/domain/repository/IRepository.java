package domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Port generic untuk operasi penyimpanan (CRUD) dan pencarian.
 * Didefinisikan di domain agar use case tidak bergantung pada teknologi penyimpanan tertentu.
 *
 * @param <T>  tipe entitas domain
 * @param <ID> tipe identifier entitas
 */
public interface IRepository<T, ID> {

    /** Mengambil semua entitas. */
    List<T> findAll();

    /** Mencari satu entitas berdasarkan ID. */
    Optional<T> findById(ID id);

    /** Menyimpan entitas baru dan mengembalikannya. */
    T save(T entity);

    /** Menghapus entitas berdasarkan ID. Mengembalikan true jika ada data yang terhapus. */
    boolean deleteById(ID id);

    /** Menyimpan perubahan pada entitas yang sudah ada. */
    void update(T entity);

    /** Mencari entitas yang memenuhi predikat tertentu. */
    List<T> findBy(Predicate<T> predicate);
}
