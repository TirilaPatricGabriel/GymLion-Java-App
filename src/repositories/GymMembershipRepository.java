package repositories;

import classes.GymMembership;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Repositories are responsible for interacting with the storage of entities. Usually a 1-to-1 relation with the
 * entities. Any entity that should be persisted, should have a Repo.
 * */
public class GymMembershipRepository implements GenericRepository<GymMembership> {

    private static GymMembership[] storage = new GymMembership[10];

    @Override
    public void add(GymMembership entity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = entity;
                return;
            }
        }
        GymMembership[] newStorage = Arrays.<GymMembership, GymMembership>copyOf(storage, 2*storage.length, GymMembership[].class);
        newStorage[storage.length] = entity;
        storage = newStorage;
    }

    @Override
    public GymMembership get(int index) {
        return storage[index];
    }

    @Override
    public void update(GymMembership entity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] == entity) {
                // TODO UPDATE
            }
        }
    }

    @Override
    public void delete(GymMembership entity) {
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

    static public ArrayList<Integer> getGymIdsOfMembershipsWithPricesWithinALimit (Integer startPrice, Integer endPrice) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                double price = storage[i].getPrice();
                if (price >= startPrice && price <= endPrice) {
                    ids.add(storage[i].getGymId());
                }
                break;
            }
        }
        return ids;
    }

    public void changeMembershipPricesOfSelectGym (Integer gymId, Integer percent) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] != null && Objects.equals(storage[i].getGymId(), gymId)) {
                storage[i].setPrice(storage[i].getPrice() + (percent * storage[i].getPrice()));
            }
        }
    }
}
