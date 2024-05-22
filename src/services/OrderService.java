package services;

import classes.Order;
import exceptions.InvalidDataException;
import repositories.OrderRepository;

import java.time.LocalDate;

public class OrderService {
    private OrderRepository orderRepo;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepo = orderRepository;
    }

    public void registerNewEntity(int customerId, LocalDate date, double price) throws InvalidDataException {
        if (customerId <= 0) {
            throw new InvalidDataException("Invalid customer ID");
        }
        if (date == null) {
            throw new InvalidDataException("Invalid date");
        }
        if (price <= 0) {
            throw new InvalidDataException("Invalid price");
        }
        Order entity = new Order(customerId, date, price);
        orderRepo.add(entity);
    }

    public Order get(int index) throws InvalidDataException {
        if (index <= 0) {
            throw new InvalidDataException("Index can't be lower than or equal to 0");
        }
        return orderRepo.get(index);
    }

    public void update(int index) throws InvalidDataException {
        if (index <= 0) {
            throw new InvalidDataException("Index can't be lower than or equal to 0");
        }
        Order order = orderRepo.get(index);
        if (order == null) {
            throw new InvalidDataException("Order not found");
        }

        orderRepo.update(order);
    }

    public void delete(int index) throws InvalidDataException {
        if (index <= 0) {
            throw new InvalidDataException("Index can't be lower than or equal to 0");
        }
        Order order = orderRepo.get(index);
        if (order == null) {
            throw new InvalidDataException("Order not found");
        }
        orderRepo.delete(order);
    }
}
