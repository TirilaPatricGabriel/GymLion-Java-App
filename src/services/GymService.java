package services;

import classes.Gym;
import exceptions.InvalidDataException;
import repositories.AthleteRepository;
import repositories.GymRepository;
import repositories.GymMembershipRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GymService {

    private GymRepository gymRepo;

    private static GymService instance;
    private AuditService auditService;

    private GymService(GymRepository repo) {
        this.gymRepo = repo;
        this.auditService = AuditService.getInstance();
    }

    public static GymService getInstance(GymRepository repo) {
        if (instance == null) {
            instance = new GymService(repo);
        }
        return instance;
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

    public List<Gym> findGymBasedOnMembershipPrices(double startPrice, double endPrice) throws InvalidDataException {
        if (startPrice <= 0 || endPrice <= 0) {
            throw new InvalidDataException("Prices can't be lower than 0");
        }
        if(endPrice < startPrice) {
            throw new InvalidDataException("End price must be higher or equal to the starting price");
        }
        auditService.logAction("Searched for gym based on prices");
        return gymRepo.findGymBasedOnMembershipPrices(startPrice, endPrice);
    }

    public void changeMembershipPrices(GymMembershipRepository membershipRepository, String gymName, Integer percent) throws InvalidDataException {
        if (gymName == null || gymName.trim().isEmpty()) {
            throw new InvalidDataException("Invalid gym name");
        }
        if (percent == 0) {
            throw new InvalidDataException("Invalid percent. Must be higher or lower than 0.");
        }
        auditService.logAction("Changing membership prices");
        gymRepo.changeMembershipPrices(membershipRepository, gymName, percent);
        System.out.println("Changes were made successfully!");
    }

    public void changeMembershipPrices(String gymName, Integer percent) {
        if (percent == null || percent < 0) {
            throw new IllegalArgumentException("Percentage must be a positive integer");
        }
        gymRepo.changeMembershipPrices(gymName, percent);
    }

    public Map<String, Integer> getGymMembershipCounts() throws SQLException {
        auditService.logAction("Get gym + membership counts");
        return gymRepo.getGymMembershipCounts();
    }
}
