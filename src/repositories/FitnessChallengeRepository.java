package repositories;

import classes.FitnessChallenge;
import config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FitnessChallengeRepository implements GenericRepository<FitnessChallenge> {

    @Override
    public void add(FitnessChallenge entity) {
        String sql = "INSERT INTO fitness_challenges (name, description, points) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getDescription());
            stmt.setInt(3, entity.getPoints());
            stmt.executeUpdate();

            // Retrieve the generated fitness challenge ID
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
    public FitnessChallenge get(int index) {
        FitnessChallenge fitnessChallenge = null;
        String sql = "SELECT * FROM fitness_challenges WHERE challengeId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, index);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                int points = rs.getInt("points");
                fitnessChallenge = new FitnessChallenge(name, description, points);
                fitnessChallenge.setId(index);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fitnessChallenge;
    }

    @Override
    public void update(FitnessChallenge entity) {
        String sql = "UPDATE fitness_challenges SET name = ?, description = ?, points = ? WHERE challengeId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getDescription());
            stmt.setInt(3, entity.getPoints());
            stmt.setInt(4, entity.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(FitnessChallenge entity) {
        String sql = "DELETE FROM fitness_challenges WHERE challengeId = ?";
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
        String sql = "SELECT COUNT(*) FROM fitness_challenges";
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

    public List<Integer> getAllCustomersThatCompletedChallenge(CustomerRepository customerRepo, String challengeName) {
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
            throw new RuntimeException(e);
        }
        return customerIds;
    }

    public void upgradeChallenge(CustomerRepository customerRepo, Integer numberOfCompletions, Integer points) {
        String sql = "UPDATE fitness_challenges SET points = points + ? WHERE challengeId = ? AND " +
                "(SELECT COUNT(*) FROM customer_fitness_challenges WHERE challengeId = ?) < ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            List<FitnessChallenge> challenges = getAllChallenges();
            for (FitnessChallenge challenge : challenges) {
                int challengeId = challenge.getId();
                stmt.setInt(1, points);
                stmt.setInt(2, challengeId);
                stmt.setInt(3, challengeId);
                stmt.setInt(4, numberOfCompletions);
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<FitnessChallenge> getAllChallenges() {
        List<FitnessChallenge> challenges = new ArrayList<>();
        String sql = "SELECT * FROM fitness_challenges";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                int points = rs.getInt("points");
                FitnessChallenge challenge = new FitnessChallenge(name, description, points);
                challenge.setId(rs.getInt("challengeId"));
                challenges.add(challenge);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return challenges;
    }
}
