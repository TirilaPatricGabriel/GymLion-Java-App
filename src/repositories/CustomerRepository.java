package repositories;

import classes.Customer;

import java.util.Arrays;

/**
 * Repositories are responsible for interacting with the storage of entities. Usually a 1-to-1 relation with the
 * entities. Any entity that should be persisted, should have a Repo.
 * */
public class CustomerRepository implements GenericRepository<Customer> {

    private Customer[] storage = new Customer[10];

    @Override
    public void add(Customer entity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = entity;
                return;
            }
        }
        Customer[] newStorage = Arrays.<Customer, Customer>copyOf(storage, 2*storage.length, Customer[].class);
        newStorage[storage.length] = entity;
        storage = newStorage;
    }

    @Override
    public Customer get(int index) {
        return storage[index];
    }

    @Override
    public void update(Customer entity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] == entity) {
                // TODO UPDATE
            }
        }
    }

    @Override
    public void delete(Customer entity) {
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
