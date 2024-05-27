package repositories;

import classes.ChallengeCustomer;
import classes.FitnessChallenge;
import config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChallengeCustomerRepository {

    public void addChallengeCustomer(ChallengeCustomer challengeCustomer) throws SQLException {
        String sql = "INSERT INTO customer_fitness_challenges (challengeId, customerId) VALUES (?, ?)";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, challengeCustomer.getChallengeId());
            stmt.setInt(2, challengeCustomer.getCustomerId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to connect customer to challenge.", e);
        }
    }

    public List<FitnessChallenge> getChallengesByCustomerId(int customerId) throws SQLException {
        String sql = "SELECT c.challengeId, c.name, c.description, c.points " +
                "FROM customer_fitness_challenges cc " +
                "JOIN fitness_challenges c ON cc.challengeId = c.challengeId " +
                "WHERE cc.customerId = ?";
        List<FitnessChallenge> challenges = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
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
            throw new SQLException("Failed to get challenges by customer id.", e);
        }
        return challenges;
    }

    public List<ChallengeCustomer> getCustomersByChallengeId(int challengeId) throws SQLException {
        String sql = "SELECT * FROM customer_fitness_challenges WHERE challengeId = ?";
        List<ChallengeCustomer> challengeCustomers = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, challengeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                challengeCustomers.add(new ChallengeCustomer(rs.getInt("challengeId"), rs.getInt("customerId")));
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get customers by challenge id.", e);
        }
        return challengeCustomers;
    }

    public void removeChallengeCustomer(ChallengeCustomer challengeCustomer) throws SQLException {
        String sql = "DELETE FROM customer_fitness_challenges WHERE challengeId = ? AND customerId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, challengeCustomer.getChallengeId());
            stmt.setInt(2, challengeCustomer.getCustomerId());
            stmt.executeUpdate();
        }
    }

    public void removeAllChallengesFromCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM customer_fitness_challenges WHERE customerId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to remove all challenges from customer.", e);
        }
    }
}
