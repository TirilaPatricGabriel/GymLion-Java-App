package repositories;

import classes.Gym;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Repositories are responsible for interacting with the storage of entities. Usually a 1-to-1 relation with the
 * entities. Any entity that should be persisted, should have a Repo.
 * */
public class GymRepository implements GenericRepository<Gym> {

    private Gym[] storage = new Gym[10];

    @Override
    public void add(Gym entity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = entity;
                return;
            }
        }
        Gym[] newStorage = Arrays.<Gym, Gym>copyOf(storage, 2*storage.length, Gym[].class);
        newStorage[storage.length] = entity;
        storage = newStorage;
    }

    @Override
    public Gym get(int index) {
        return storage[index];
    }

    public ArrayList<String> findGymsBasedOnMembershipPrices(GymMembershipRepository membershipRepository, Integer startPrice, Integer endPrice) {
        startPrice = (startPrice != null && startPrice >= 0) ? startPrice : 0;
        endPrice = (endPrice != null && endPrice >= 0) ? endPrice : Integer.MAX_VALUE;
        ArrayList<String> gyms = new ArrayList<>();
        ArrayList<Integer> gymIds = GymMembershipRepository.getGymIdsOfMembershipsWithPricesWithinALimit(startPrice, endPrice);

        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                if (gymIds.contains(storage[i].getId())) {
                    gyms.add(storage[i].getName());
                }
                break;
            }
        }

        return gyms;
    }

    public void changeMembershipPrices (GymMembershipRepository membershipRepository, String gymName, Integer percent) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] != null && storage[i].getName().equals(gymName)) {
                membershipRepository.changeMembershipPricesOfSelectGym(storage[i].getId(), percent);
            }
        }
    }
    @Override
    public void update(Gym entity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] == entity) {
                // TODO UPDATE
            }
        }
    }

    @Override
    public void delete(Gym entity) {
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
