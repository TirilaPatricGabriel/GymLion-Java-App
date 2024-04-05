package repositories;

import classes.Investor;

import java.util.Arrays;

/**
 * Repositories are responsible for interacting with the storage of entities. Usually a 1-to-1 relation with the
 * entities. Any entity that should be persisted, should have a Repo.
 * */
public class InvestorRepository implements GenericRepository<Investor> {


    private Investor[] storage = new Investor[10];

    @Override
    public void add(Investor entity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = entity;
                return;
            }
        }
        Investor[] newStorage = Arrays.<Investor, Investor>copyOf(storage, 2*storage.length, Investor[].class);
        newStorage[storage.length] = entity;
        storage = newStorage;
    }

    @Override
    public Investor get(int index) {
        return storage[index];
    }

    @Override
    public void update(Investor entity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] == entity) {
                // TODO UPDATE
            }
        }
    }

    @Override
    public void delete(Investor entity) {
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
