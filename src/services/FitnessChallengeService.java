package services;

import classes.Customer;
import classes.Event;
import classes.FitnessChallenge;
import classes.Location;
import exceptions.InvalidDataException;
import repositories.CustomerRepository;
import repositories.FitnessChallengeRepository;
import repositories.GymRepository;

import java.time.LocalDate;
import java.util.ArrayList;

public class FitnessChallengeService {

    private FitnessChallengeRepository challengesRepo = new FitnessChallengeRepository();


    public FitnessChallengeService(FitnessChallengeRepository repo) {
        this.challengesRepo = repo;
    }

    public void registerNewEntity(String name, String description, Integer points) throws InvalidDataException {
        FitnessChallenge entity = new FitnessChallenge(name, description, points);
        challengesRepo.add(entity);
    }

    public FitnessChallenge get(int index) throws InvalidDataException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        return challengesRepo.get(index);
    }

    public void update(int index) throws InvalidDataException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        FitnessChallenge chall = challengesRepo.get(index);
        challengesRepo.update(chall);
    }

    public void delete(int index) throws InvalidDataException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        FitnessChallenge chall = challengesRepo.get(index);
        challengesRepo.delete(chall);
    }

//    public ArrayList<Integer> getAllCustomersThatCompletedChallenge (CustomerRepository customerRepo, String challengeName) throws InvalidDataException {
//        if (challengeName == null || challengeName == "") {
//            throw new InvalidDataException("The name of the challenge is wrong!");
//        }
//        return challengesRepo.getAllCustomersThatCompletedChallenge(customerRepo, challengeName);
//    }

//    public void upgradeChallenge(CustomerRepository customerRepo, Integer numberOfCompletions, Integer points) throws InvalidDataException {
//        if (numberOfCompletions < 0) {
//            throw new InvalidDataException("Number of completions field is wrong!");
//        }
//        challengesRepo.upgradeChallenge(customerRepo, numberOfCompletions, points);
//    }
}