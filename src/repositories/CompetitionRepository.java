package repositories;

import classes.Competition;

import java.util.Arrays;

/**
 * Repositories are responsible for interacting with the storage of entities. Usually a 1-to-1 relation with the
 * entities. Any entity that should be persisted, should have a Repo.
 * */
public class CompetitionRepository implements GenericRepository<Competition> {

    private Competition[] storage = new Competition[10];

    @Override
    public void add(Competition entity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = entity;
                return;
            }
        }
        Competition[] newStorage = Arrays.<Competition, Competition>copyOf(storage, 2*storage.length, Competition[].class);
        newStorage[storage.length] = entity;
        storage = newStorage;
    }

    @Override
    public Competition get(int index) {
        return storage[index];
    }

    @Override
    public void update(Competition entity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] == entity) {
                // TODO UPDATE
            }
        }
    }

    @Override
    public void delete(Competition entity) {
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
}
