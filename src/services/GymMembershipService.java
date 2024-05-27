package services;

import classes.GymMembership;
import exceptions.InvalidDataException;
import repositories.AthleteRepository;
import repositories.GymMembershipRepository;

import java.sql.SQLException;

public class GymMembershipService {
    private GymMembershipRepository gymMembershipRepo;

    private static GymMembershipService instance;
    private AuditService auditService;

    private GymMembershipService(GymMembershipRepository repo) {
        this.gymMembershipRepo = repo;
        this.auditService = AuditService.getInstance();
    }

    public static GymMembershipService getInstance(GymMembershipRepository repo) {
        if (instance == null) {
            instance = new GymMembershipService(repo);
        }
        return instance;
    }

    public void registerNewEntity(int gymId, double price, int durationInMonths, String code) throws InvalidDataException, SQLException {
        if (gymId <= 0) {
            throw new InvalidDataException("Invalid gym ID");
        }
        if (price <= 0) {
            throw new InvalidDataException("Invalid price");
        }
        if (durationInMonths <= 0) {
            throw new InvalidDataException("Invalid duration in months");
        }
        if (code == null || code.trim().isEmpty()) {
            throw new InvalidDataException("Invalid code");
        }
        GymMembership entity = new GymMembership(gymId, price, durationInMonths, code);
        gymMembershipRepo.add(entity);
    }

    public GymMembership get(int index) throws InvalidDataException, SQLException {
        if (index <= 0) {
            throw new InvalidDataException("Index can't be lower than or equal to 0");
        }
        return gymMembershipRepo.get(index);
    }

    public void update(int index) throws InvalidDataException, SQLException {
        if (index <= 0) {
            throw new InvalidDataException("Index can't be lower than or equal to 0");
        }
        GymMembership membership = gymMembershipRepo.get(index);
        if (membership == null) {
            throw new InvalidDataException("Gym membership not found");
        }

        gymMembershipRepo.update(membership);
    }

    public void delete(int index) throws InvalidDataException, SQLException {
        if (index <= 0) {
            throw new InvalidDataException("Index can't be lower than or equal to 0");
        }
        GymMembership membership = gymMembershipRepo.get(index);
        if (membership == null) {
            throw new InvalidDataException("Gym membership not found");
        }
        gymMembershipRepo.delete(membership);
    }
}
