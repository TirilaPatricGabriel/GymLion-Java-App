package repositories;

import classes.Athlete;
import classes.Customer;
import config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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
    public void add(Customer entity) {
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer get(int id) {
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
                        String name = persons.getString("name");
                        int age = persons.getInt("age");
                        String email = persons.getString("email");
                        String phone = persons.getString("phone");
                        String address = persons.getString("address");

                        customer = new Customer(name, email, phone, address, age, balance);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    @Override
    public void update(Customer entity) {
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
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Customer entity) {
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
            throw new RuntimeException(e);
        }
    }





















































//    @Override
//    public void add(Customer entity) {
//        for (int i=0; i<storage.length; i++) {
//            if (storage[i] == null) {
//                storage[i] = entity;
//                return;
//            }
//        }
//        Customer[] newStorage = Arrays.<Customer, Customer>copyOf(storage, 2*storage.length, Customer[].class);
//        newStorage[storage.length] = entity;
//        storage = newStorage;
//    }
//
//    @Override
//    public Customer get(int index) {
//        for (int i=0; i<storage.length; i++) {
//            if (storage[i] != null && storage[i].getId() == index) {
//                return storage[i];
//            }
//        }
//        return storage[0];
//    }
//
//    @Override
//    public void update(Customer entity) {
//        for (int i=0; i<storage.length; i++) {
//            if (storage[i] == entity) {
//                // TODO UPDATE
//            }
//        }
//    }
//
//    @Override
//    public void delete(Customer entity) {
//        for (int i = 0; i < storage.length; i++) {
//            if (storage[i] != null && storage[i] == entity) {
//                storage[i] = null;
//                break;
//            }
//        }
//    }

    @Override
    public int getSize() {
        return storage.length;
    }

    public int getCustomerWithMostOrders (OrderRepository orderRepo) {
        Integer max = -1, customerId = -1;
        for (int i=0; i<storage.length; i++) {
            if (storage[i] != null) {
                Integer nrOfOrders = orderRepo.getNumberOfOrdersOfCustomer(storage[i].getId());
                if (nrOfOrders > max) {
                    max = nrOfOrders;
                    customerId = storage[i].getId();
                }
            }
        }
        return customerId;
    }

//    public ArrayList<Integer> getAllCustomersThatCompletedChallenge(int challengeId) {
//        ArrayList<Integer> customerIds = new ArrayList<>();
//        for (int i=0; i<storage.length; i++) {
//            if (storage[i] != null) {
//                ArrayList<Integer> challengeIds = storage[i].getChallengesCompletedIds();
//                if (challengeIds.contains(challengeId)) {
//                    customerIds.add(storage[i].getId());
//                }
//            }
//        }
//        return customerIds;
//    }

    public Integer rewardCustomerOfTheMonth (OrderRepository orderRepo, Integer reward) {
        int customerId = this.getCustomerWithMostOrders(orderRepo);
        System.out.println("CUSTOMER OF THE MONTH ID:" + customerId);
        for (int i=0; i<storage.length; i++) {
            if (storage[i] != null && storage[i].getId() == customerId) {
                storage[i].setBalance(storage[i].getBalance() + reward);
                break;
            }
        }
        return customerId;
    }
}
