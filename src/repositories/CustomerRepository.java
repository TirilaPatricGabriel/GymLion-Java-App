package repositories;

import classes.Athlete;
import classes.Customer;
import config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Repositories are responsible for interacting with the storage of entities. Usually a 1-to-1 relation with the
 * entities. Any entity that should be persisted, should have a Repo.
 * */
public class CustomerRepository implements GenericRepository<Customer> {

    private Customer[] storage = new Customer[10];
    private static Scanner scanner = new Scanner(System.in);

    @Override
    public void add(Customer entity) throws SQLException {
        String sql = "CALL INSERT_CUSTOMER(?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConfiguration.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, entity.getName());
            stmt.setString(3, entity.getEmail());
            stmt.setString(4, entity.getPhone());
            stmt.setString(5, entity.getAddress());
            stmt.setDouble(6, entity.getAge());
            stmt.setDouble(7, entity.getBalance());
            stmt.execute();
        } catch (SQLException e) {
            throw new SQLException("Failed to add a new customer.", e);
        }
    }

    @Override
    public Customer get(int id) throws SQLException {
        Customer customer = null;

        String customerSql = "SELECT * FROM customers WHERE customerId = ?";
        String personSql = "SELECT * FROM persons WHERE id = ?";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement customerQuery = connection.prepareStatement(customerSql)) {
            customerQuery.setInt(1, id);
            ResultSet customers = customerQuery.executeQuery();

            if (customers.next()) {
                double balance = customers.getDouble("balance");

                try (PreparedStatement personQuery = connection.prepareStatement(personSql)) {
                    personQuery.setInt(1, id);
                    ResultSet persons = personQuery.executeQuery();

                    if (persons.next()) {
                        Integer customerId = persons.getInt("id");
                        String name = persons.getString("name");
                        int age = persons.getInt("age");
                        String email = persons.getString("email");
                        String phone = persons.getString("phone");
                        String address = persons.getString("address");

                        customer = new Customer(customerId, name, email, phone, address, age, balance);
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get customer.", e);
        }

        return customer;
    }

    @Override
    public void update(Customer entity) throws SQLException {
        String updatePersonSql = "UPDATE persons SET name = ?, email = ?, phone = ?, address = ?, age = ? WHERE id = ?";
        String updateCustomerSql = "UPDATE customers SET balance = ? WHERE customerId = ?";

        System.out.println("New name:");
        String name = scanner.nextLine();
        System.out.println("New email:");
        String  email = scanner.nextLine();
        System.out.println("New phone:");
        String  phone = scanner.nextLine();
        System.out.println("New address:");
        String  address = scanner.nextLine();
        System.out.println("New age:");
        Integer age = Integer.parseInt(scanner.nextLine());
        System.out.println("New balance:");
        Integer balance = Integer.parseInt(scanner.nextLine());

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement personQuery = connection.prepareStatement(updatePersonSql);
             PreparedStatement customerQuery = connection.prepareStatement(updateCustomerSql)) {

            personQuery.setString(1, name);
            personQuery.setString(2, email);
            personQuery.setString(3, phone);
            personQuery.setString(4, address);
            personQuery.setInt(5, age);
            personQuery.setInt(6, entity.getId());
            personQuery.executeUpdate();

            customerQuery.setDouble(1, balance);
            customerQuery.setInt(2, entity.getId());
            customerQuery.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to update customer.", e);
        }
    }

    @Override
    public void delete(Customer entity) throws SQLException {
        String deleteCustomerSql = "DELETE FROM customers WHERE customerId = ?";
        String deletePersonSql = "DELETE FROM persons WHERE id = ?";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement customerQuery = connection.prepareStatement(deleteCustomerSql);
             PreparedStatement personQuery = connection.prepareStatement(deletePersonSql)) {

            customerQuery.setInt(1, entity.getId());
            customerQuery.executeUpdate();

            personQuery.setInt(1, entity.getId());
            personQuery.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to delete customer.", e);
        }
    }

    public Customer getCustomerWithMostOrders() throws SQLException {
        String sql = "SELECT c.*, p.*, COUNT(o.orderId) as orderCount " +
                "FROM customers c, persons p, orders o " +
                "WHERE o.customerId = c.customerId AND p.id = c.customerId " +
                "GROUP BY c.customerId " +
                "ORDER BY orderCount DESC " +
                "LIMIT 1";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                int id = rs.getInt("customerId");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int age = rs.getInt("age");
                int balance = rs.getInt("balance");
                Customer customer = new Customer(id, name, email, phone, address, age, balance);

                return customer;
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get customer with most orders.", e);
        }

        return null;
    }

    public void rewardCustomerWithMostOrders(int id, double reward) throws SQLException {
        String sql = "UPDATE customers SET balance = balance + ? WHERE customerId = ?";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setDouble(1, reward);
            stmt.setInt(2, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to reward customer with most orders.", e);
        }
    }

    public List<Customer> findUsersThatCompletedChallenge(int challengeId) throws SQLException {
        String sql = "SELECT c.*, p.* FROM customers c, persons p " +
                "WHERE p.id = c.customerId AND EXISTS (" +
                "    SELECT 1 FROM customer_fitness_challenges cc " +
                "    WHERE c.customerId = cc.customerId AND cc.challengeId = ?" +
                ")";

        List<Customer> customers = new ArrayList<>();

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, challengeId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("customerId");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    int age = rs.getInt("age");
                    int balance = rs.getInt("balance");

                    Customer customer = new Customer(id, name, email, phone, address, age, balance);

                    customers.add(customer);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get the users that completed the challenge.", e);
        }

        return customers;
    }

    public void showCustomersWithBalanceOverThreshold(Integer threshold) throws SQLException {
        String sql = "SELECT c.*, p.* FROM customers c, persons p " +
                "WHERE p.id = c.customerId AND c.balance > ?";

        List<Customer> customers = new ArrayList<>();

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, threshold);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.setId(rs.getInt("customerId"));
                    customer.setName(rs.getString("name"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setAddress(rs.getString("address"));
                    customer.setAge(rs.getInt("age"));
                    customer.setBalance(rs.getDouble("balance"));
                    customers.add(customer);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to show customers with balance over threshold.", e);
        }

        customers.forEach(System.out::println);
    }





















































    @Override
    public int getSize() {
        return storage.length;
    }
}
