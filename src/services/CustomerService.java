package services;

import classes.Athlete;
import classes.Customer;
import classes.FitnessChallenge;
import exceptions.InvalidDataException;
import repositories.AthleteRepository;
import repositories.CustomerRepository;
import repositories.GymRepository;
import repositories.OrderRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private CustomerRepository customerRepo;

    private static CustomerService instance;
    private AuditService auditService;

    private CustomerService(CustomerRepository repo) {
        this.customerRepo = repo;
        this.auditService = AuditService.getInstance();
    }

    public static CustomerService getInstance(CustomerRepository repo) {
        if (instance == null) {
            instance = new CustomerService(repo);
        }
        return instance;
    }

    public void registerNewEntity(String name, String email, String phone, String address, int age, double balance) throws InvalidDataException, SQLException {
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

        if (balance < 0) {
            throw new InvalidDataException("Invalid balance");
        }

        Customer entity = new Customer(name, email, phone, address, age, balance);
        customerRepo.add(entity);
    }

    public Customer get(int id) throws InvalidDataException, SQLException {
        if (id < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        return customerRepo.get(id);
    }

    public void update(int id) throws InvalidDataException, SQLException {
        if (id < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        Customer customer = customerRepo.get(id);
        customerRepo.update(customer);
    }

    public void delete(int id) throws InvalidDataException, SQLException {
        if (id < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        Customer atl = customerRepo.get(id);
        customerRepo.delete(atl);
    }

    public Customer getCustomerWithMostOrders () throws SQLException {
        auditService.logAction("Search for customer with most orders");
        return customerRepo.getCustomerWithMostOrders();
    }

    public void rewardCustomerWithMostOrders(int id, double reward) throws SQLException, InvalidDataException {
        if (reward < 0) {
            throw new InvalidDataException("Reward can't be lower than 0");
        }

        customerRepo.rewardCustomerWithMostOrders(id, reward);
        auditService.logAction("Customer with most orders rewarded");
    }

    public List<Customer> getCustomersThatCompletedChallenge(int id) throws SQLException {
        auditService.logAction("Searched for customers that completed a challenge");
        return customerRepo.findUsersThatCompletedChallenge(id);
    }

    public void showCustomersWithBalanceOverThreshold(Integer threshold) throws SQLException, InvalidDataException {
        if (threshold < 0) {
            throw new InvalidDataException("Threshold can't be lower than 0");
        }

        customerRepo.showCustomersWithBalanceOverThreshold(threshold);
        auditService.logAction("Searched for customers with balance bigger than threshold");
    }
}