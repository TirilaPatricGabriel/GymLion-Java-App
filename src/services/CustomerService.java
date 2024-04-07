package services;

import classes.Athlete;
import classes.Customer;
import classes.FitnessChallenge;
import exceptions.InvalidDataException;
import repositories.CustomerRepository;
import repositories.OrderRepository;

import java.util.ArrayList;

public class CustomerService {
    private CustomerRepository customerRepo = new CustomerRepository();

    public void registerNewEntity(String name, String email, String phone, String address, double balance, ArrayList<FitnessChallenge> challengesCompleted) throws InvalidDataException {

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

        if (balance < 0) {
            throw new InvalidDataException("Invalid balance");
        }

        if (challengesCompleted == null) {
            throw new InvalidDataException("Invalid challenges");
        }

        Customer entity = new Customer(name, email, phone, address, balance, challengesCompleted);
        customerRepo.add(entity);
    }

    public Customer get(int index) throws InvalidDataException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        return customerRepo.get(index);
    }

    public void update(int index) throws InvalidDataException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        Customer customer = customerRepo.get(index);
        customerRepo.update(customer);
    }

    public void delete(int index) throws InvalidDataException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        Customer atl = customerRepo.get(index);
        customerRepo.delete(atl);
    }

    public int getCustomerWithMostOrders (OrderRepository orderRepo) {
        return customerRepo.getCustomerWithMostOrders(orderRepo);
    }

    public void rewardCustomerOfTheMonth (OrderRepository orderRepo, Integer reward) throws InvalidDataException {
        if (reward == null || reward <= 0) {
            throw new InvalidDataException("Reward must be higher than zero!");
        }
        customerRepo.rewardCustomerOfTheMonth(orderRepo, reward);
    }
}