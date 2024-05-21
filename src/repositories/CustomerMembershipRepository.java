package repositories;

import classes.CustomerMembership;
import config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerMembershipRepository {

    public void addCustomerMembership(CustomerMembership customerMembership) throws SQLException {
        String sql = "INSERT INTO customer_memberships (membershipId, customerId) VALUES (?, ?)";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerMembership.getMembershipId());
            stmt.setInt(2, customerMembership.getCustomerId());
            stmt.executeUpdate();
        }
    }

    public List<CustomerMembership> getMembershipsByCustomerId(int customerId) throws SQLException {
        String sql = "SELECT * FROM customer_memberships WHERE customerId = ?";
        List<CustomerMembership> customerMemberships = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                customerMemberships.add(new CustomerMembership(rs.getInt("membershipId"), rs.getInt("customerId")));
            }
        }
        return customerMemberships;
    }

    public List<CustomerMembership> getCustomersByMembershipId(int membershipId) throws SQLException {
        String sql = "SELECT * FROM customer_memberships WHERE membershipId = ?";
        List<CustomerMembership> customerMemberships = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, membershipId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                customerMemberships.add(new CustomerMembership(rs.getInt("membershipId"), rs.getInt("customerId")));
            }
        }
        return customerMemberships;
    }

    public void removeCustomerMembership(CustomerMembership customerMembership) throws SQLException {
        String sql = "DELETE FROM customer_memberships WHERE membershipId = ? AND customerId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerMembership.getMembershipId());
            stmt.setInt(2, customerMembership.getCustomerId());
            stmt.executeUpdate();
        }
    }

    public void removeAllMembershipsFromCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM customer_memberships WHERE customerId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.executeUpdate();
        }
    }
}
