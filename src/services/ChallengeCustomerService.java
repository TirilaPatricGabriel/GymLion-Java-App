package services;

import classes.ChallengeCustomer;
import classes.FitnessChallenge;
import repositories.ChallengeCustomerRepository;

import java.sql.SQLException;
import java.util.List;

public class ChallengeCustomerService {

    private ChallengeCustomerRepository challengeCustomerRepository;

    private static ChallengeCustomerService instance;
    private AuditService auditService;

    private ChallengeCustomerService(ChallengeCustomerRepository repo) {
        this.challengeCustomerRepository = repo;
        this.auditService = AuditService.getInstance();
    }

    public static ChallengeCustomerService getInstance(ChallengeCustomerRepository repo) {
        if (instance == null) {
            instance = new ChallengeCustomerService(repo);
        }
        return instance;
    }

    public void addChallengeCustomer(int challengeId, int customerId) throws SQLException {
        ChallengeCustomer challengeCustomer = new ChallengeCustomer(challengeId, customerId);
        challengeCustomerRepository.addChallengeCustomer(challengeCustomer);
    }

    public List<FitnessChallenge> getChallengesByCustomerId(int customerId) throws SQLException {
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
