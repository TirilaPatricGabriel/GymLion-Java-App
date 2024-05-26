package services;

import repositories.AthleteRepository;
import classes.Athlete;
import exceptions.InvalidDataException;
import repositories.FitnessChallengeRepository;
import repositories.GymRepository;


import java.util.ArrayList;
import java.util.List;

public class AthleteService {

    private AthleteRepository athleteRepository;

    private static AthleteService instance;
    private AuditService auditService;

    private AthleteService(AthleteRepository repo) {
        this.athleteRepository = repo;
        this.auditService = AuditService.getInstance();
    }

    public static AthleteService getInstance(AthleteRepository repo) {
        if (instance == null) {
            instance = new AthleteService(repo);
        }
        return instance;
    }

    public void registerNewEntity(String name, String email, String phone, String address, int age, double salary, int socialMediaFollowers, double bonusPerTenThousandLikes) throws InvalidDataException {

        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Invalid name");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new InvalidDataException("Invalid email");
        }

        if (phone == null || phone.trim().isEmpty()) {
            throw new InvalidDataException("Invalid phone");
        }

        if (address == null || address.trim().isEmpty()) {
            throw new InvalidDataException("Invalid address");
        }

        if (age < 18) {
            throw new InvalidDataException("Invalid age");
        }

        if (salary < 0) {
            throw new InvalidDataException("Invalid salary");
        }

        if (socialMediaFollowers < 0) {
            throw new InvalidDataException("Invalid followers");
        }

        if (bonusPerTenThousandLikes < 0) {
            throw new InvalidDataException("Invalid bonus");
        }

        Athlete entity = new Athlete(name, email, phone, address, age, salary, socialMediaFollowers, bonusPerTenThousandLikes);
        athleteRepository.add(entity);
    }

    public Athlete get(int id) throws InvalidDataException {
        if (id < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        return athleteRepository.get(id);
    }

    public void update(int id) throws InvalidDataException {
        if (id < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        Athlete atl = athleteRepository.get(id);
        athleteRepository.update(atl);
    }

    public void delete(int id) throws InvalidDataException {
        if (id < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        Athlete atl = athleteRepository.get(id);
        athleteRepository.delete(atl);
    }

    public void deleteExpensiveAthletes(int percentage) throws InvalidDataException {
        if (percentage < 0) {
            throw new InvalidDataException("Percentage can't be lower than 0");
        }
        List<Athlete> expensiveAthletes = athleteRepository.getExpensiveAthletes(percentage);
        for (Athlete athlete : expensiveAthletes) {
            Athlete atl = athleteRepository.get(athlete.getId());
            athleteRepository.delete(atl);
        }
        auditService.logAction("Deleted expensive athletes");
    }

    public void increaseSalaryOfPopularAthletes(Integer followers, Double percentage) throws InvalidDataException {
        if (followers < 0) {
            throw new InvalidDataException("Folowers can't be lower than 0");
        }
        if (percentage < 0) {
            throw new InvalidDataException("Percentage can't be lower than 0");
        }
        athleteRepository.increaseSalaryOfPopularAthletes(followers, percentage);
        auditService.logAction("Updated salary of popular athletes");
    }


}