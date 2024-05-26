package services;

import classes.Order;
import exceptions.InvalidDataException;
import repositories.AthleteRepository;
import repositories.OrderRepository;

import java.time.LocalDate;

public class OrderService {
    private OrderRepository orderRepo;

    private static OrderService instance;
    private AuditService auditService;

    private OrderService(OrderRepository repo) {
        this.orderRepo = repo;
        this.auditService = AuditService.getInstance();
    }

    public static OrderService getInstance(OrderRepository repo) {
        if (instance == null) {
            instance = new OrderService(repo);
        }
        return instance;
    }

    public Integer registerNewEntity(int customerId, LocalDate date, double price) throws InvalidDataException {
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

        return entity.getId();
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
