package adapter.repository;

import domain.repository.IRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Basis generic repository yang menyimpan data di memori.
 * Menghilangkan duplikasi kode penyimpanan, pencarian, dan penghapusan untuk semua entitas.
 *
 * @param <T>  tipe entitas domain
 * @param <ID> tipe identifier entitas
 */
public abstract class InMemoryRepository<T, ID> implements IRepository<T, ID> {

    private final List<T> data = new ArrayList<>();
    private final Function<T, ID> idExtractor;

    protected InMemoryRepository(Function<T, ID> idExtractor) {
        this.idExtractor = idExtractor;
    }

    @Override
    public List<T> findAll() {
        // Salinan defensif: perubahan di luar tidak memengaruhi data internal.
        return new ArrayList<>(data);
    }

    @Override
    public Optional<T> findById(ID id) {
        return data.stream()
                .filter(item -> hasId(item, id))
                .findFirst();
    }

    @Override
    public T save(T entity) {
        data.add(entity);
        return entity;
    }

    @Override
    public boolean deleteById(ID id) {
        return data.removeIf(item -> hasId(item, id));
    }

    @Override
    public void update(T entity) {
        ID id = idExtractor.apply(entity);
        for (int index = 0; index < data.size(); index++) {
            if (hasId(data.get(index), id)) {
                data.set(index, entity);
                return;
            }
        }
    }

    @Override
    public List<T> findBy(Predicate<T> predicate) {
        return data.stream()
                .filter(predicate)
                .toList();
    }

    private boolean hasId(T item, ID id) {
        return idExtractor.apply(item).equals(id);
    }
}
