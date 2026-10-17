package adapter.repository;

import domain.entity.Activity;
import domain.repository.IActivityRepository;

/**
 * Implementasi in-memory {@link IActivityRepository} berbasis {@link InMemoryRepository}.
 */
public class ActivityRepository extends InMemoryRepository<Activity, Integer> implements IActivityRepository {

    private int lastId = 0;

    public ActivityRepository() {
        super(Activity::getId);
    }

    @Override
    public Activity save(String title, String day, String time) {
        return save(new Activity(++lastId, title, day, time));
    }
}
