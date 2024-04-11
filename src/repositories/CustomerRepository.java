package repositories;

import classes.Customer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

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
        for (int i=0; i<storage.length; i++) {
            if (storage[i] != null && storage[i].getId() == index) {
                return storage[i];
            }
        }
        return storage[0];
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

    public int getCustomerWithMostOrders (OrderRepository orderRepo) {
        Integer max = -1, customerId = -1;
        for (int i=0; i<storage.length; i++) {
            if (storage[i] != null) {
                Integer nrOfOrders = orderRepo.getNumberOfOrdersOfCustomer(storage[i].getId());
                if (nrOfOrders > max) {
                    max = nrOfOrders;
                    customerId = storage[i].getId();
                }
            }
        }
        return customerId;
    }

    public ArrayList<Integer> getAllCustomersThatCompletedChallenge(int challengeId) {
        ArrayList<Integer> customerIds = new ArrayList<>();
        for (int i=0; i<storage.length; i++) {
            if (storage[i] != null) {
                ArrayList<Integer> challengeIds = storage[i].getChallengesCompletedIds();
                if (challengeIds.contains(challengeId)) {
                    customerIds.add(storage[i].getId());
                }
            }
        }
        return customerIds;
    }

    public Integer rewardCustomerOfTheMonth (OrderRepository orderRepo, Integer reward) {
        int customerId = this.getCustomerWithMostOrders(orderRepo);
        System.out.println("CUSTOMER OF THE MONTH ID:" + customerId);
        for (int i=0; i<storage.length; i++) {
            if (storage[i] != null && storage[i].getId() == customerId) {
                storage[i].setBalance(storage[i].getBalance() + reward);
                break;
            }
        }
        return customerId;
    }
}
