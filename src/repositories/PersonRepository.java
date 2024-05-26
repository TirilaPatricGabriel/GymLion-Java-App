package repositories;

import classes.Person;
import classes.Customer;
import classes.Athlete;
import config.DatabaseConfiguration;

import java.sql.*;
import java.util.*;

/**
 * Repositories are responsible for interacting with the storage of entities. Usually a 1-to-1 relation with the
 * entities. Any entity that should be persisted, should have a Repo.
 * */
public class PersonRepository implements GenericRepository<Person> {

    private Set<Person> storage;

    public PersonRepository() {
        this.storage = new TreeSet<>();
    }

    @Override
    public void add(Person entity) {
        storage.add(entity);
    }

    @Override
    public Person get(int index) {
        Person pers = new Person("", "", "", "", 20);
        return pers;
    }

    @Override
    // Update method for a Person object
    public void update(Person entity) {
//        // Find the Person object
//        Iterator<Person> iterator = this.storage.iterator();
//        while (iterator.hasNext()) {
//            Person person = iterator.next();
//            if (person.equals(entity)) {
//                iterator.remove();
//
//                person.update();
//
//                this.storage.add(person);
//            }
//        }
    }

    @Override
    public void delete(Person entity) {
        Iterator<Person> iterator = this.storage.iterator();
        while (iterator.hasNext()) {
            Person person = iterator.next();
            if (person.equals(entity)) {
                iterator.remove();
            }
        }
    }

    @Override
    public int getSize() {
        return 0;
    }

    public void showCustomersWithBalanceOverThreshold(double threshold) {
        System.out.println("Customers with high balance:");
        for (Person person : storage) {
            if (person instanceof Customer) {
                Customer customer = (Customer) person;
                if (customer.getBalance() > threshold) {
                    System.out.println(customer);
                }
            }
        }
    }

    public void increaseSalaryOfPopularAthletes() {
        System.out.println("Updating salary for popular Athletes:");
        storage.forEach(person -> {
            if (person instanceof Athlete) {
                Athlete athlete = (Athlete) person;
                if (athlete.getSocialMediaFollowers() > 50000) {
                    double newSalary = athlete.getSalary() * 1.1;
                    athlete.setSalary(newSalary);
                    System.out.println("Increased salary for: " + athlete);
                }
            }
        });
    }

    public List<Person> showPeopleSortedByAge() {
        String sql = "SELECT * FROM persons ORDER BY age";

        List<Person> people = new ArrayList<>();

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int age = rs.getInt("age");

                Person person = new Person(id, name, email, phone, address, age);

                people.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return people;
    }
}
