package repositories;

import classes.FitnessChallenge;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Repositories are responsible for interacting with the storage of entities. Usually a 1-to-1 relation with the
 * entities. Any entity that should be persisted, should have a Repo.
 * */
public class FitnessChallengeRepository implements GenericRepository<FitnessChallenge> {

    private FitnessChallenge[] storage = new FitnessChallenge[10];

    @Override
    public void add(FitnessChallenge entity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = entity;
                return;
            }
        }
        FitnessChallenge[] newStorage = Arrays.<FitnessChallenge, FitnessChallenge>copyOf(storage, 2*storage.length, FitnessChallenge[].class);
        newStorage[storage.length] = entity;
        storage = newStorage;
    }

    @Override
    public FitnessChallenge get(int index) {
        return storage[index];
    }

    @Override
    public void update(FitnessChallenge entity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] == entity) {
                // TODO UPDATE
            }
        }
    }

    @Override
    public void delete(FitnessChallenge entity) {
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

//    public ArrayList<Integer> getAllCustomersThatCompletedChallenge (CustomerRepository customerRepo, String challengeName) {
//        System.out.println(challengeName);
//        int challengeId = -1;
//        for (int i=0; i<storage.length; i++) {
//            if (storage[i] != null && storage[i].getName().equals(challengeName)) {
//                challengeId = storage[i].getId();
//            }
//        }
//        return customerRepo.getAllCustomersThatCompletedChallenge(challengeId);
//    }

//    public void upgradeChallenge(CustomerRepository customerRepo, Integer numberOfCompletions, Integer points) {
//        for (int i=0; i<storage.length; i++) {
//            if (storage[i] != null && getAllCustomersThatCompletedChallenge(customerRepo, storage[i].getName()).size() < numberOfCompletions) {
//                storage[i].setPoints(storage[i].getPoints() + points);
//            }
//        }
//    }
}
