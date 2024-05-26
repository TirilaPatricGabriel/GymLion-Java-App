package repositories;

import classes.Order;
import config.DatabaseConfiguration;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderRepository implements GenericRepository<Order> {

    private static Scanner scanner = new Scanner(System.in);

    @Override
    public void add(Order entity) {
        String sql = "INSERT INTO orders (customerId, date, price) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, entity.getCustomerId());
            stmt.setDate(2, Date.valueOf(entity.getDate()));
            stmt.setDouble(3, entity.getPrice());
            stmt.executeUpdate();

            // Retrieve the generated order ID
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                entity.setId(generatedId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order get(int index) {
        Order order = null;
        String sql = "SELECT * FROM orders WHERE orderId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, index);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int orderId = rs.getInt("orderId");
                int customerId = rs.getInt("customerId");
                Date date = rs.getDate("date");
                double price = rs.getDouble("price");
                order = new Order(orderId, customerId, date.toLocalDate(), price);
                order.setId(index);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return order;
    }

    @Override
    public void update(Order entity) {
        String sql = "UPDATE orders SET customerId = ?, date = ?, price = ? WHERE orderId = ?";

        System.out.println("Enter new customer ID:");
        int newCustomerId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter new date (YYYY-MM-DD):");
        LocalDate newDate = LocalDate.parse(scanner.nextLine());
        System.out.println("Enter new price:");
        double newPrice = Double.parseDouble(scanner.nextLine());

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, newCustomerId);
            stmt.setDate(2, Date.valueOf(newDate));
            stmt.setDouble(3, newPrice);
            stmt.setInt(4, entity.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Order entity) {
        String sql = "DELETE FROM orders WHERE orderId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, entity.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getSize() {
        String sql = "SELECT COUNT(*) FROM orders";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public Integer getNumberOfOrdersOfCustomer(int customerId) {
        String sql = "SELECT COUNT(*) FROM orders WHERE customerId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
