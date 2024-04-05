package repositories;

import classes.Event;

import java.util.Arrays;

/**
 * Repositories are responsible for interacting with the storage of entities. Usually a 1-to-1 relation with the
 * entities. Any entity that should be persisted, should have a Repo.
 * */
public class EventRepository implements GenericRepository<Event> {

    private Event[] storage = new Event[10];

    @Override
    public void add(Event entity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = entity;
                return;
            }
        }
        Event[] newStorage = Arrays.<Event, Event>copyOf(storage, 2*storage.length, Event[].class);
        newStorage[storage.length] = entity;
        storage = newStorage;
    }

    @Override
    public Event get(int index) {
        return storage[index];
    }

    @Override
    public void update(Event entity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] == entity) {
                // TODO UPDATE
            }
        }
    }

    @Override
    public void delete(Event entity) {
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
