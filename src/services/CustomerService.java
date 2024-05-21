package services;

import classes.Athlete;
import classes.Customer;
import classes.FitnessChallenge;
import exceptions.InvalidDataException;
import repositories.CustomerRepository;
import repositories.GymRepository;
import repositories.OrderRepository;

import java.util.ArrayList;

public class CustomerService {
    private CustomerRepository customerRepo = new CustomerRepository();

    public CustomerService(CustomerRepository repo) {
        this.customerRepo = repo;
    }

    public void registerNewEntity(String name, String email, String phone, String address, int age, double balance) throws InvalidDataException {
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

    public Integer rewardCustomerOfTheMonth (OrderRepository orderRepo, Integer reward) throws InvalidDataException {
        if (reward == null || reward <= 0) {
            throw new InvalidDataException("Reward must be higher than zero!");
        }
        return customerRepo.rewardCustomerOfTheMonth(orderRepo, reward);
    }
}