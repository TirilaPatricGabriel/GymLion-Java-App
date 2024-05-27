package services;

import classes.Customer;
import classes.Event;
import classes.FitnessChallenge;
import classes.Location;
import exceptions.InvalidDataException;
import repositories.CustomerRepository;
import repositories.FitnessChallengeRepository;
import repositories.GymRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FitnessChallengeService {

    private FitnessChallengeRepository challengesRepo;
    private static FitnessChallengeService instance;
    private AuditService auditService;

    private FitnessChallengeService(FitnessChallengeRepository repo) {
        this.challengesRepo = repo;
        this.auditService = AuditService.getInstance();
    }

    public static FitnessChallengeService getInstance(FitnessChallengeRepository repo) {
        if (instance == null) {
            instance = new FitnessChallengeService(repo);
        }
        return instance;
    }

    public void registerNewEntity(String name, String description, Integer points) throws InvalidDataException, SQLException {
        if (name == null || name.isEmpty()) {
            throw new InvalidDataException("Name can't be empty");
        }

        if (description == null || description.isEmpty()) {
            throw new InvalidDataException("Description can't be empty");
        }

        if (points <= 10) {
            throw new InvalidDataException("Points need to be at least 10");
        }

        FitnessChallenge entity = new FitnessChallenge(name, description, points);
        challengesRepo.add(entity);
    }

    public FitnessChallenge get(int index) throws InvalidDataException, SQLException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        return challengesRepo.get(index);
    }

    public void update(int index) throws InvalidDataException, SQLException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        FitnessChallenge chall = challengesRepo.get(index);
        challengesRepo.update(chall);
    }

    public void delete(int index) throws InvalidDataException, SQLException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        FitnessChallenge chall = challengesRepo.get(index);
        challengesRepo.delete(chall);
    }

    public List<FitnessChallenge> getAllChallenges() throws SQLException {
        return challengesRepo.getAllChallenges();
    }

    public void upgradeChallengeCompletedLessThanNTimes(Integer n, Integer points) throws InvalidDataException, SQLException {
        if (n < 0) {
            throw new InvalidDataException("The number can't be lower than 0");
        }
        if (points <= 0) {
            throw new InvalidDataException("Points need to be higher than 0");
        }


        challengesRepo.upgradeChallenge(n, points);
        auditService.logAction("Searched for challenges completed less than n times");

    }

    public List<FitnessChallenge> getAllChallengesSortedByPoints() {
        auditService.logAction("Searched for challenges sorted by points");

        try {
            List<FitnessChallenge> challenges = challengesRepo.getAllChallenges();
            challenges.sort(Comparator.comparingInt(FitnessChallenge::getPoints));
            return challenges;
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }
}
