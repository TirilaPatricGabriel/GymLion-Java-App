package services;

import classes.ChallengeCustomer;
import repositories.ChallengeCustomerRepository;

import java.sql.SQLException;
import java.util.List;

public class ChallengeCustomerService {

    private ChallengeCustomerRepository challengeCustomerRepository;

    public ChallengeCustomerService(ChallengeCustomerRepository challengeCustomerRepository) {
        this.challengeCustomerRepository = challengeCustomerRepository;
    }

    public void addChallengeCustomer(int challengeId, int customerId) throws SQLException {
        ChallengeCustomer challengeCustomer = new ChallengeCustomer(challengeId, customerId);
        challengeCustomerRepository.addChallengeCustomer(challengeCustomer);
    }

    public List<ChallengeCustomer> getChallengesByCustomerId(int customerId) throws SQLException {
        return challengeCustomerRepository.getChallengesByCustomerId(customerId);
    }

    public List<ChallengeCustomer> getCustomersByChallengeId(int challengeId) throws SQLException {
        return challengeCustomerRepository.getCustomersByChallengeId(challengeId);
    }

    public void removeChallengeCustomer(int challengeId, int customerId) throws SQLException {
        ChallengeCustomer challengeCustomer = new ChallengeCustomer(challengeId, customerId);
        challengeCustomerRepository.removeChallengeCustomer(challengeCustomer);
    }

    public void removeAllChallengesFromCustomer(int customerId) throws SQLException {
        challengeCustomerRepository.removeAllChallengesFromCustomer(customerId);
    }
}
