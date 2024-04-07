package repositories;

import classes.Event;

import java.util.Arrays;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;


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

    public ArrayList<HashMap<String, String>> getAllEventsFromAPlaceWithinAPeriod (LocalDate startDate, LocalDate endDate, String countryName) {
        ArrayList<HashMap<String, String>> res = new ArrayList<>();
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null && storage[i].getStartDate().compareTo(startDate) >= 0 && storage[i].getEndDate().compareTo(endDate) <= 0 && Objects.equals(storage[i].getLocation().getCountryName(), countryName)) {
                HashMap<String, String> obj = new HashMap<>();
                obj.put("name", storage[i].getName());
                obj.put("description", storage[i].getDescription());
                break;
            }
        }
        return res;
    }

    public void deleteAllEventsThatAlreadyTookPlace () {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] != null && storage[i].getEndDate().compareTo(LocalDate.now()) < 0) {
                storage[i] = null;
            }
        }
    }

    public Integer getNumberOfEventsFromLocation(Integer locId) {
        Integer nr = 0;
        for (int i=0; i<storage.length; i++) {
            if (storage[i].getLocation().getId() == locId) {
                nr += 1;
            }
        }
        return nr;
    }
}
