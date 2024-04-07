package repositories;

import classes.Order;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Repositories are responsible for interacting with the storage of entities. Usually a 1-to-1 relation with the
 * entities. Any entity that should be persisted, should have a Repo.
 * */
public class OrderRepository implements GenericRepository<Order> {

    private Order[] storage = new Order[10];

    @Override
    public void add(Order entity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = entity;
                return;
            }
        }
        Order[] newStorage = Arrays.<Order, Order>copyOf(storage, 2*storage.length, Order[].class);
        newStorage[storage.length] = entity;
        storage = newStorage;
    }

    @Override
    public Order get(int index) {
        return storage[index];
    }

    @Override
    public void update(Order entity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] == entity) {
                // TODO UPDATE
            }
        }
    }

    @Override
    public void delete(Order entity) {
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

    public Integer getNumberOfOrdersOfCustomer(int customerId) {
        Integer number = 0;
        for (int i=0; i<storage.length; i++) {
            if (storage[i] != null && storage[i].getCustomerId() == customerId) {
                number += 1;
            }
        }
        return number;
    }
}
