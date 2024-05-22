package services;

import classes.Gym;
import exceptions.InvalidDataException;
import repositories.GymRepository;
import repositories.GymMembershipRepository;

import java.util.ArrayList;
import java.util.List;

public class GymService {

    private GymRepository gymRepo;

    public GymService(GymRepository gymRepository) {
        this.gymRepo = gymRepository;
    }

    public void registerNewEntity(String name, String description, Integer capacity, Integer locationId) throws InvalidDataException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Invalid name");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new InvalidDataException("Invalid description");
        }
        if (capacity == null || capacity <= 0) {
            throw new InvalidDataException("Invalid capacity");
        }
        if (locationId == null || locationId <= 0) {
            throw new InvalidDataException("Invalid location ID");
        }
        Gym entity = new Gym(name, description, capacity, locationId);
        gymRepo.add(entity);
    }

    public Gym get(int index) throws InvalidDataException {
        if (index <= 0) {
            throw new InvalidDataException("Index can't be lower than or equal to 0");
        }
        return gymRepo.get(index);
    }

    public void update(int index) throws InvalidDataException {
        if (index <= 0) {
            throw new InvalidDataException("Index can't be lower than or equal to 0");
        }
        Gym gym = gymRepo.get(index);

        gymRepo.update(gym);
    }

    public void delete(int index) throws InvalidDataException {
        if (index <= 0) {
            throw new InvalidDataException("Index can't be lower than or equal to 0");
        }
        Gym gym = gymRepo.get(index);
        if (gym == null) {
            throw new InvalidDataException("Gym not found");
        }
        gymRepo.delete(gym);
    }

    public List<String> findGymsBasedOnMembershipPrices(Double startPrice, Double endPrice) throws InvalidDataException {
        if (startPrice == null || startPrice < 0) {
            throw new InvalidDataException("Invalid start price");
        }
        if (endPrice == null || endPrice < 0) {
            throw new InvalidDataException("Invalid end price");
        }
        GymMembershipRepository membershipRepository = new GymMembershipRepository();
        return gymRepo.findGymsBasedOnMembershipPrices(startPrice, endPrice, membershipRepository);
    }

    public void changeMembershipPrices(GymMembershipRepository membershipRepository, String gymName, Integer percent) throws InvalidDataException {
        if (gymName == null || gymName.trim().isEmpty()) {
            throw new InvalidDataException("Invalid gym name");
        }
        if (percent == 0) {
            throw new InvalidDataException("Invalid percent. Must be higher or lower than 0.");
        }
        gymRepo.changeMembershipPrices(membershipRepository, gymName, percent);
        System.out.println("Changes were made successfully!");
    }
}
