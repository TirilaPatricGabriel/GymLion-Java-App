package repositories;

import classes.Athlete;
import classes.OrderProduct;
import config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AthleteRepository implements GenericRepository<Athlete> {

    private Athlete[] storage = new Athlete[100];
    private static Scanner scanner = new Scanner(System.in);

    @Override
    public void add(Athlete entity) throws SQLException {
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
            throw new SQLException("Failed to register a new athlete in the database.", e);
        }
    }

    @Override
    public Athlete get(int id) throws SQLException {
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
                        Integer athleteId = personRs.getInt("id");
                        String name = personRs.getString("name");
                        int age = personRs.getInt("age");
                        String email = personRs.getString("email");
                        String phone = personRs.getString("phone");
                        String address = personRs.getString("address");

                        athlete = new Athlete(athleteId, name, email, phone, address, age, salary, socialMediaFollowers, bonusPerTenThousandLikes);
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get the athlete from the database.", e);
        }
        return athlete;
    }

    @Override
    public void update(Athlete entity) throws SQLException {
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
            throw new SQLException("Failed to update the athlete.", e);
        }
    }

    @Override
    public void delete(Athlete entity) throws SQLException {
        String deleteCompetitionAthletesSql = "DELETE FROM competition_athletes WHERE athleteId = ?";
        String deleteAthleteSql = "DELETE FROM athletes WHERE athleteId = ?";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement competitionAthleteStmt = connection.prepareStatement(deleteCompetitionAthletesSql);
             PreparedStatement athleteStmt = connection.prepareStatement(deleteAthleteSql)) {

            connection.setAutoCommit(false);

            competitionAthleteStmt.setInt(1, entity.getId());
            competitionAthleteStmt.executeUpdate();

            athleteStmt.setInt(1, entity.getId());
            athleteStmt.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            throw new SQLException("Failed to delete the athlete.", e);
        }
    }


    public List<Athlete> getExpensiveAthletes(double percentage) throws SQLException {
        List<Athlete> expensiveAthletes = new ArrayList<>();
        String sql = "SELECT a.*, p.name, p.age, p.email, p.phone, p.address FROM athletes a JOIN persons p ON a.athleteId = p.id WHERE (a.salary * (? / 100)) > a.socialMediaFollowers";

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
            throw new SQLException("Failed to get expensive athletes.", e);
        }
        return expensiveAthletes;
    }

    public void increaseSalaryOfPopularAthletes(Integer followers, Double percentage) throws SQLException {
        String updateAthletesSql = "UPDATE athletes SET salary = salary + (salary * ? / 100) WHERE socialMediaFollowers > ?";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(updateAthletesSql)) {

            stmt.setDouble(1, percentage);
            stmt.setInt(2, followers);

            int rowsUpdated = stmt.executeUpdate();
            System.out.println("Number of salaries updated: " + rowsUpdated);
        } catch (SQLException e) {
            throw new SQLException("Failed to increase the salaries.", e);
        }
    }

    @Override
    public int getSize() {
        return storage.length;
    }
}
