package repositories;

import classes.Athlete;
import classes.OrderProduct;
import config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Repositories are responsible for interacting with the storage of entities. Usually a 1-to-1 relation with the
 * entities. Any entity that should be persisted, should have a Repo.
 * */
public class AthleteRepository implements GenericRepository<Athlete> {

    private Athlete[] storage = new Athlete[100];
    private static Scanner scanner = new Scanner(System.in);

    @Override
    public void add(Athlete entity) {
        String sql = "CALL INSERT_ATHLETE(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConfiguration.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, entity.getName());
            stmt.setString(3, entity.getEmail());
            stmt.setString(4, entity.getPhone());
            stmt.setString(5, entity.getAddress());
            stmt.setInt(6, entity.getAge());
            stmt.setDouble(7, entity.getSalary());
            stmt.setDouble(8, entity.getBonusPerTenThousandLikes());
            stmt.setInt(9, entity.getSocialMediaFollowers());
            stmt.execute();

            int generatedId = stmt.getInt(1);
            entity.setId(generatedId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Athlete get(int id) {
        Athlete athlete = null;

        String athleteSql = "SELECT * FROM athletes WHERE athleteId = ?";
        String personSql = "SELECT * FROM persons WHERE id = ?";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement athleteStmt = connection.prepareStatement(athleteSql)) {
            athleteStmt.setInt(1, id);
            ResultSet athleteRs = athleteStmt.executeQuery();

            if (athleteRs.next()) {
                double salary = athleteRs.getDouble("salary");
                double bonusPerTenThousandLikes = athleteRs.getDouble("bonusPerTenThousandLikes");
                int socialMediaFollowers = athleteRs.getInt("socialMediaFollowers");

                try (PreparedStatement personStmt = connection.prepareStatement(personSql)) {
                    personStmt.setInt(1, id);
                    ResultSet personRs = personStmt.executeQuery();

                    if (personRs.next()) {
                        String name = personRs.getString("name");
                        int age = personRs.getInt("age");
                        String email = personRs.getString("email");
                        String phone = personRs.getString("phone");
                        String address = personRs.getString("address");

                        athlete = new Athlete(name, email, phone, address, age, salary, socialMediaFollowers, bonusPerTenThousandLikes);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return athlete;
    }

    @Override
    public void update(Athlete entity) {
        String updatePersonSql = "UPDATE persons SET name = ?, email = ?, phone = ?, address = ?, age = ? WHERE id = ?";
        String updateAthleteSql = "UPDATE athletes SET salary = ?, bonusPerTenThousandLikes = ?, socialMediaFollowers = ? WHERE athleteId = ?";

        System.out.println("New name:");
        String name = scanner.nextLine();
        System.out.println("New email:");
        String email = scanner.nextLine();
        System.out.println("New phone:");
        String phone = scanner.nextLine();
        System.out.println("New address:");
        String address = scanner.nextLine();
        System.out.println("New age:");
        Integer age = Integer.parseInt(scanner.nextLine());
        System.out.println("New salary:");
        Double salary = Double.parseDouble(scanner.nextLine());
        System.out.println("New bonus:");
        Integer bonus = Integer.parseInt(scanner.nextLine());
        System.out.println("New followers:");
        Integer followers = Integer.parseInt(scanner.nextLine());

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement personStmt = connection.prepareStatement(updatePersonSql);
             PreparedStatement athleteStmt = connection.prepareStatement(updateAthleteSql)) {

            personStmt.setString(1, name);
            personStmt.setString(2, email);
            personStmt.setString(3, phone);
            personStmt.setString(4, address);
            personStmt.setInt(5, age);
            personStmt.setInt(6, entity.getId());
            personStmt.executeUpdate();

            athleteStmt.setDouble(1, salary);
            athleteStmt.setDouble(2, bonus);
            athleteStmt.setInt(3, followers);
            athleteStmt.setInt(4, entity.getId());
            athleteStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Athlete entity) {
        String deleteAthleteSql = "DELETE FROM athletes WHERE athleteId = ?";
        String deletePersonSql = "DELETE FROM persons WHERE id = ?";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement athleteStmt = connection.prepareStatement(deleteAthleteSql);
             PreparedStatement personStmt = connection.prepareStatement(deletePersonSql)) {

            // Delete from athletes table
            athleteStmt.setInt(1, entity.getId());
            athleteStmt.executeUpdate();

            // Delete from persons table
            personStmt.setInt(1, entity.getId());
            personStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Athlete> getExpensiveAthletes(double percentage) {
        List<Athlete> expensiveAthletes = new ArrayList<>();
        String sql = "SELECT a.*, p.name, p.age, p.email, p.phone, p.address, FROM athletes a JOIN persons p ON a.athleteId = p.id WHERE (a.socialMediaFollowers * ?) < a.salary";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, percentage / 100);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int athleteId = rs.getInt("athleteId");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                double salary = rs.getDouble("salary");
                double bonusPerTenThousandLikes = rs.getDouble("bonusPerTenThousandLikes");
                int socialMediaFollowers = rs.getInt("socialMediaFollowers");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");

                Athlete athlete =  athlete = new Athlete(name, email, phone, address, age, salary, socialMediaFollowers, bonusPerTenThousandLikes);
                athlete.setId(athleteId);
                expensiveAthletes.add(athlete);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return expensiveAthletes;
    }









































    @Override
    public int getSize() {
        return storage.length;
    }

//    public void deleteExpensiveAthletes (Integer percent) {
//        for (int i=0; i<storage.length; i++) {
//            if (storage[i] != null && (storage[i].getSocialMediaFollowers() * ((double) percent / 100)) < storage[i].getSalary()) {
//                System.out.println("percent: " + percent);
//                storage[i] = null;
//            }
//        }
//    }
}
