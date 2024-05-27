package repositories;

import classes.Investor;
import config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvestorRepository implements GenericRepository<Investor> {

    @Override
    public void add(Investor entity) throws SQLException {
        String sql = "INSERT INTO investors (name, contractValue) VALUES (?, ?)";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getName());
            stmt.setDouble(2, entity.getContractValue());
            stmt.executeUpdate();

            // Retrieve the generated investor ID
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                entity.setId(generatedId);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to add new investor.", e);
        }
    }

    @Override
    public Investor get(int index) throws SQLException {
        Investor investor = null;
        String sql = "SELECT * FROM investors WHERE investorId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, index);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                double contractValue = rs.getDouble("contractValue");
                investor = new Investor(name, contractValue);
                investor.setId(index);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get investor.", e);
        }
        return investor;
    }

    @Override
    public void update(Investor entity) throws SQLException {
        String sql = "UPDATE investors SET name = ?, contractValue = ? WHERE investorId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getName());
            stmt.setDouble(2, entity.getContractValue());
            stmt.setInt(3, entity.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to update investor.", e);
        }
    }

    @Override
    public void delete(Investor entity) throws SQLException {
        String sql = "DELETE FROM investors WHERE investorId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, entity.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to delete investor.", e);
        }
    }

    @Override
    public int getSize() throws SQLException {
        String sql = "SELECT COUNT(*) FROM investors";
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
}
