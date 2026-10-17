package usecase;

import domain.entity.Activity;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IActivityRepository;
import java.util.List;

/**
 * Logika bisnis jadwal kegiatan.
 * Tidak melakukan I/O; kegagalan dilaporkan lewat checked exception domain.
 */
public class ActivityUseCase {

    private static final String ENTITY_NAME = "Kegiatan";

    private final IActivityRepository activityRepository;

    public ActivityUseCase(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    /**
     * Menambah kegiatan baru.
     *
     * @throws ValidationException jika judul, hari, atau waktu kosong
     */
    public Activity addActivity(String title, String day, String time) throws ValidationException {
        validateNotBlank(title, "Judul kegiatan");
        validateNotBlank(day, "Hari");
        validateNotBlank(time, "Waktu");
        return activityRepository.save(title.trim(), day.trim(), time.trim());
    }

    /**
     * Mengubah kegiatan secara parsial: parameter {@code null} berarti field tidak diubah.
     * Seluruh input divalidasi terlebih dahulu sehingga kegiatan tidak pernah terubah sebagian.
     *
     * @throws EntityNotFoundException jika ID tidak ditemukan
     * @throws ValidationException     jika salah satu field yang diubah kosong
     */
    public void updateActivity(int id, String title, String day, String time)
            throws EntityNotFoundException, ValidationException {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME, id));

        if (title != null) {
            validateNotBlank(title, "Judul kegiatan");
        }
        if (day != null) {
            validateNotBlank(day, "Hari");
        }
        if (time != null) {
            validateNotBlank(time, "Waktu");
        }

        if (title != null) {
            activity.changeTitle(title.trim());
        }
        if (day != null) {
            activity.changeDay(day.trim());
        }
        if (time != null) {
            activity.changeTime(time.trim());
        }
        activityRepository.update(activity);
    }

    /** @throws EntityNotFoundException jika ID tidak ditemukan */
    public void removeActivity(int id) throws EntityNotFoundException {
        if (!activityRepository.deleteById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
    }

    /** Mencari kegiatan berdasarkan judul (tidak membedakan huruf besar/kecil). */
    public List<Activity> searchActivities(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return activityRepository.findBy(activity -> activity.getTitle().toLowerCase().contains(lowerKeyword));
    }

    public List<Activity> sortActivities(SortOption option) {
        return activityRepository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }

    private void validateNotBlank(String value, String fieldName) throws ValidationException {
        if (value == null || value.isBlank()) {
            throw new ValidationException(fieldName + " tidak boleh kosong");
        }
    }
}
