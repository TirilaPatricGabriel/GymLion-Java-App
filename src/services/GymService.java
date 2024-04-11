package services;

import classes.Customer;
import classes.FitnessChallenge;
import classes.Location;
import exceptions.InvalidDataException;
import classes.Gym;
import repositories.GymRepository;
import repositories.GymMembershipRepository;


import java.util.ArrayList;

public class GymService {

    private GymRepository gymRepo = new GymRepository();

    public GymService(GymRepository gymRepository) {
        this.gymRepo = gymRepository;
    }

    public void registerNewEntity(String name, String description, Integer capacity, Integer locationId) throws InvalidDataException {
        Gym entity = new Gym(name, description, capacity, locationId);
        gymRepo.add(entity);
    }

    public Gym get(int index) throws InvalidDataException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        return gymRepo.get(index);
    }

    public void update(int index) throws InvalidDataException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        Gym gym = gymRepo.get(index);
        gymRepo.update(gym);
    }

    public void delete(int index) throws InvalidDataException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        Gym gym = gymRepo.get(index);
        gymRepo.delete(gym);
    }

    public ArrayList<String> findGymsBasedOnMembershipPrices(Double startPrice, Double endPrice) throws InvalidDataException {
        if (startPrice == null || startPrice < 0) {
            throw new InvalidDataException("Invalid start price");
        }
        if (endPrice == null || endPrice < 0) {
            throw new InvalidDataException("Invalid end price");
        }
        return gymRepo.findGymsBasedOnMembershipPrices(startPrice, endPrice);
    }

    public void changeMembershipPrices (GymMembershipRepository membershipRepository, String gymName, Integer percent) throws InvalidDataException {
        if (gymName == null || gymName == "") {
            throw new InvalidDataException("Invalid gym name");
        }
        if (percent == 0) {
            throw new InvalidDataException("Invalid percent. Must be higher or lower than 0.");
        }
        gymRepo.changeMembershipPrices(membershipRepository, gymName, percent);
        System.out.println("Changes were made successfully!");
    }

}