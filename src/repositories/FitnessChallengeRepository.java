package repositories;

import classes.FitnessChallenge;
import config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FitnessChallengeRepository implements GenericRepository<FitnessChallenge> {

    private static Scanner scanner = new Scanner(System.in);

    @Override
    public void add(FitnessChallenge entity) throws SQLException {
        String sql = "INSERT INTO fitness_challenges (name, description, points) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getDescription());
            stmt.setInt(3, entity.getPoints());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                entity.setId(generatedId);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to add new challenge.", e);
        }
    }

    @Override
    public FitnessChallenge get(int index) throws SQLException {
        FitnessChallenge fitnessChallenge = null;
        String sql = "SELECT * FROM fitness_challenges WHERE challengeId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, index);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Integer id = rs.getInt("challengeId");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int points = rs.getInt("points");
                fitnessChallenge = new FitnessChallenge(id, name, description, points);
                fitnessChallenge.setId(index);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get challenge.", e);
        }
        return fitnessChallenge;
    }

    @Override
    public void update(FitnessChallenge entity) throws SQLException {
        String sql = "UPDATE fitness_challenges SET name = ?, description = ?, points = ? WHERE challengeId = ?";
        System.out.println("New name:");
        String name = scanner.nextLine();
        System.out.println("New description:");
        String description = scanner.nextLine();
        System.out.println("New points:");
        Integer points = Integer.parseInt(scanner.nextLine());

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setInt(3, points);
            stmt.setInt(4, entity.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to update challenge.", e);
        }
    }

    @Override
    public void delete(FitnessChallenge entity) throws SQLException {
        String sql = "DELETE FROM fitness_challenges WHERE challengeId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, entity.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to delete challenge.", e);
        }
    }

    @Override
    public int getSize() throws SQLException {
        String sql = "SELECT COUNT(*) FROM fitness_challenges";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get size.", e);
        }
        return 0;
    }

    public List<Integer> getAllCustomersThatCompletedChallenge(CustomerRepository customerRepo, String challengeName) throws SQLException {
        List<Integer> customerIds = new ArrayList<>();
        String sql = "SELECT c.customerId FROM customer_fitness_challenges cfc " +
                "JOIN fitness_challenges fc ON cfc.challengeId = fc.challengeId " +
                "WHERE fc.name = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, challengeName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                customerIds.add(rs.getInt("customerId"));
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get all customers that completed a challenge.", e);
        }
        return customerIds;
    }

    public void upgradeChallenge(Integer numberOfCompletions, Integer points) throws SQLException {
        String selectSql = "SELECT challengeId FROM fitness_challenges fc " +
                "WHERE (SELECT COUNT(*) FROM customer_fitness_challenges cfc WHERE cfc.challengeId = fc.challengeId) < ?";
        String updateSql = "UPDATE fitness_challenges SET points = points + ? WHERE challengeId = ?";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(selectSql);
             PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {

            selectStmt.setInt(1, numberOfCompletions);
            ResultSet rs = selectStmt.executeQuery();
            List<Integer> challengeIdsToUpdate = new ArrayList<>();

            while (rs.next()) {
                challengeIdsToUpdate.add(rs.getInt("challengeId"));
            }

            for (Integer challengeId : challengeIdsToUpdate) {
                updateStmt.setInt(1, points);
                updateStmt.setInt(2, challengeId);
                updateStmt.addBatch();
            }

            updateStmt.executeBatch();

        } catch (SQLException e) {
            throw new SQLException("Failed to upgrade challenge.", e);
        }
    }

    public List<FitnessChallenge> getAllChallenges() throws SQLException {
        String sql = "SELECT challengeId, name, description, points FROM fitness_challenges";
        List<FitnessChallenge> challenges = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("challengeId");
                String name = rs.getString("name");
                String description = rs.getString("description");
                Integer points = rs.getInt("points");

                FitnessChallenge challenge = new FitnessChallenge(name, description, points);
                challenges.add(challenge);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get all challenges.", e);
        }
        return challenges;
    }
}
