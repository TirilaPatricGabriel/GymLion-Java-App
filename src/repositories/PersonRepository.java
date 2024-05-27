package repositories;

import classes.Person;
import classes.Customer;
import classes.Athlete;
import config.DatabaseConfiguration;

import java.sql.*;
import java.util.*;

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
    public void update(Person entity) {
    }

    @Override
    public void delete(Person entity) {
    }

    @Override
    public int getSize() {
        return 0;
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
