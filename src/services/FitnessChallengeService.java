package services;

import classes.FitnessChallenge;
import exceptions.InvalidDataException;
import repositories.CustomerRepository;
import repositories.FitnessChallengeRepository;

import java.util.ArrayList;

public class FitnessChallengeService {

    private FitnessChallengeRepository challengesRepo = new FitnessChallengeRepository();

    public ArrayList<Integer> getAllCustomersThatCompletedChallenge (CustomerRepository customerRepo, String challengeName) throws InvalidDataException {
        if (challengeName == null || challengeName == "") {
            throw new InvalidDataException("The name of the challenge is wrong!");
        }
        return challengesRepo.getAllCustomersThatCompletedChallenge(customerRepo, challengeName);
    }

    public void upgradeChallenge(CustomerRepository customerRepo, Integer numberOfCompletions, Integer points) throws InvalidDataException {
        if (numberOfCompletions < 0) {
            throw new InvalidDataException("Number of completions field is wrong!");
        }
        challengesRepo.upgradeChallenge(customerRepo, numberOfCompletions, points);
    }
}