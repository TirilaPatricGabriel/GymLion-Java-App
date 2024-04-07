package repositories;

import classes.Athlete;

import java.util.Arrays;

/**
 * Repositories are responsible for interacting with the storage of entities. Usually a 1-to-1 relation with the
 * entities. Any entity that should be persisted, should have a Repo.
 * */
public class AthleteRepository implements GenericRepository<Athlete> {

    private Athlete[] storage = new Athlete[100];

    @Override
    public void add(Athlete entity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = entity;
                return;
            }
        }
        Athlete[] newStorage = Arrays.<Athlete, Athlete>copyOf(storage, 2*storage.length, Athlete[].class);
        newStorage[storage.length] = entity;
        storage = newStorage;
    }

    @Override
    public Athlete get(int index) {
        return storage[index];
    }

    @Override
    public void update(Athlete entity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] == entity) {
                // TODO UPDATE
            }
        }
    }

    @Override
    public void delete(Athlete entity) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null && storage[i] == entity) {
                storage[i] = null;
                break;
            }
        }
    }

    @Override
    public int getSize() {
        return storage.length;
    }

    public void deleteExpensiveAthletes (Integer percent) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] != null && (storage[i].getSocialMediaFollowers() * ((double) percent / 100)) < storage[i].getSalary()) {
                System.out.println("percent: " + percent);
                storage[i] = null;
            }
        }
    }
}
