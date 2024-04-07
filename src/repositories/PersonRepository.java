package repositories;

import classes.Person;

import java.util.Arrays;

/**
 * Repositories are responsible for interacting with the storage of entities. Usually a 1-to-1 relation with the
 * entities. Any entity that should be persisted, should have a Repo.
 * */
public class PersonRepository implements GenericRepository<Person> {

    private Person[] storage = new Person[10];

    @Override
    public void add(Person entity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = entity;
                return;
            }
        }
        Person[] newStorage = Arrays.<Person, Person>copyOf(storage, 2*storage.length, Person[].class);
        newStorage[storage.length] = entity;
        storage = newStorage;
    }

    @Override
    public Person get(int index) {
        return storage[index];
    }

    @Override
    public void update(Person entity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] == entity) {
                // TODO UPDATE
            }
        }
    }

    @Override
    public void delete(Person entity) {
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