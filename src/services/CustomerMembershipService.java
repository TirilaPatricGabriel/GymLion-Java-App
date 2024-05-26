package services;

import classes.CustomerMembership;
import repositories.AthleteRepository;
import repositories.CustomerMembershipRepository;

import java.sql.SQLException;
import java.util.List;

public class CustomerMembershipService {

    private CustomerMembershipRepository customerMembershipRepository;

    private static CustomerMembershipService instance;
    private AuditService auditService;

    private CustomerMembershipService(CustomerMembershipRepository repo) {
        this.customerMembershipRepository = repo;
        this.auditService = AuditService.getInstance();
    }

    public static CustomerMembershipService getInstance(CustomerMembershipRepository repo) {
        if (instance == null) {
            instance = new CustomerMembershipService(repo);
        }
        return instance;
    }
    public void addCustomerMembership(int membershipId, int customerId) throws SQLException {
        CustomerMembership customerMembership = new CustomerMembership(membershipId, customerId);
        customerMembershipRepository.addCustomerMembership(customerMembership);
    }

    public List<CustomerMembership> getMembershipsByCustomerId(int customerId) throws SQLException {
        return customerMembershipRepository.getMembershipsByCustomerId(customerId);
    }

    public List<CustomerMembership> getCustomersByMembershipId(int membershipId) throws SQLException {
        return customerMembershipRepository.getCustomersByMembershipId(membershipId);
    }

    public void removeCustomerMembership(int membershipId, int customerId) throws SQLException {
        CustomerMembership customerMembership = new CustomerMembership(membershipId, customerId);
        customerMembershipRepository.removeCustomerMembership(customerMembership);
    }

    public void removeAllMembershipsFromCustomer(int customerId) throws SQLException {
        customerMembershipRepository.removeAllMembershipsFromCustomer(customerId);
    }
}
