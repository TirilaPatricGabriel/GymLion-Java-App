package repositories;

import classes.GymMembership;
import config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GymMembershipRepository implements GenericRepository<GymMembership> {

    private static Scanner scanner = new Scanner(System.in);
    @Override
    public void add(GymMembership entity) throws SQLException {
        String sql = "CALL INSERT_GYM_MEMBERSHIP(?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConfiguration.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setInt(2, entity.getGymId());
            stmt.setInt(3, entity.getDurationInMonths());
            stmt.setDouble(4, entity.getPrice());
            stmt.setString(5, entity.getCode());
            stmt.execute();

            int generatedId = stmt.getInt(1);
            entity.setId(generatedId);
        } catch (SQLException e) {
            throw new SQLException("Failed to add new membership.", e);
        }
    }

    @Override
    public GymMembership get(int index) throws SQLException {
        GymMembership membership = null;
        String sql = "SELECT gm.membershipId, gm.gymId, gm.durationInMonths, p.price, p.code " +
                "FROM gym_memberships gm JOIN products p ON gm.productId = p.productId WHERE gm.membershipId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, index);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int gymId = rs.getInt("gymId");
                int durationInMonths = rs.getInt("durationInMonths");
                double price = rs.getDouble("price");
                String code = rs.getString("code");
                membership = new GymMembership(gymId, price, durationInMonths, code);
                membership.setId(rs.getInt("membershipId"));
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get membership.", e);
        }
        return membership;
    }

    @Override
    public void update(GymMembership entity) throws SQLException {
        String sql = "UPDATE gym_memberships gm JOIN products p ON gm.productId = p.productId " +
                "SET gm.gymId = ?, gm.durationInMonths = ?, p.price = ?, p.code = ? WHERE gm.membershipId = ?";

        System.out.println("Enter new gym ID:");
        int gymId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter new price:");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.println("Enter new duration in months:");
        int duration = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter new code:");
        String code = scanner.nextLine();

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, gymId);
            stmt.setInt(2, duration);
            stmt.setDouble(3, price);
            stmt.setString(4, code);
            stmt.setInt(5, entity.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to update membership.", e);
        }
    }

    @Override
    public void delete(GymMembership entity) throws SQLException {
        String sql = "DELETE FROM gym_memberships WHERE membershipId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, entity.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to delete membership.", e);
        }
    }

    @Override
    public int getSize() throws SQLException {
        String sql = "SELECT COUNT(*) FROM gym_memberships";
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

    public List<Integer> getGymIdsOfMembershipsWithPricesWithinALimit(Double startPrice, Double endPrice) throws SQLException {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT gm.gymId FROM gym_memberships gm JOIN products p ON gm.productId = p.productId " +
                "WHERE p.price BETWEEN ? AND ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, startPrice);
            stmt.setDouble(2, endPrice);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("gymId"));
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get gym ids of memberships.", e);
        }
        return ids;
    }

    public void changeMembershipPricesOfSelectGym(Integer gymId, Integer percent) throws SQLException {
        String sql = "UPDATE products p JOIN gym_memberships gm ON p.productId = gm.productId " +
                "SET p.price = p.price + (p.price * ? / 100) WHERE gm.gymId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, percent);
            stmt.setInt(2, gymId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to change membership prices.", e);
        }
    }
}
