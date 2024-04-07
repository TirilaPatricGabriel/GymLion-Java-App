package services;

import classes.Customer;
import exceptions.InvalidDataException;
import repositories.CustomerRepository;
import repositories.OrderRepository;

import java.util.ArrayList;

public class CustomerService {
    private CustomerRepository customerRepo = new CustomerRepository();

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