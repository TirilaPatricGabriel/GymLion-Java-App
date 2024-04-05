package repositories;

import classes.Location;

import java.util.Arrays;

/**
 * Repositories are responsible for interacting with the storage of entities. Usually a 1-to-1 relation with the
 * entities. Any entity that should be persisted, should have a Repo.
 * */
public class LocationRepository implements GenericRepository<Location> {

    private Location[] storage = new Location[10];

    @Override
    public void add(Location entity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = entity;
                return;
            }
        }
        Location[] newStorage = Arrays.<Location, Location>copyOf(storage, 2*storage.length, Location[].class);
        newStorage[storage.length] = entity;
        storage = newStorage;
    }

    @Override
    public Location get(int index) {
        return storage[index];
    }

    @Override
    public void update(Location entity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] == entity) {
                // TODO UPDATE
            }
        }
    }

    @Override
    public void delete(Location entity) {
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
