package repositories;

import classes.Gym;
import config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GymRepository implements GenericRepository<Gym> {

    @Override
    public void add(Gym entity) {
        String sql = "INSERT INTO gyms (name, description, capacity, locationId) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getDescription());
            stmt.setInt(3, entity.getCapacity());
            stmt.setInt(4, entity.getLocationId());
            stmt.executeUpdate();

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
    public Gym get(int index) {
        Gym gym = null;
        String sql = "SELECT * FROM gyms WHERE gymId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, index);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                int capacity = rs.getInt("capacity");
                int locationId = rs.getInt("locationId");
                gym = new Gym(name, description, capacity, locationId);
                gym.setId(index);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return gym;
    }

    public List<String> findGymsBasedOnMembershipPrices(Double startPrice, Double endPrice, GymMembershipRepository membershipRepository) {
        List<String> gyms = new ArrayList<>();
        List<Integer> gymIds = membershipRepository.getGymIdsOfMembershipsWithPricesWithinALimit(startPrice, endPrice);

        if (!gymIds.isEmpty()) {
            String sql = "SELECT name FROM gyms WHERE gymId IN (" + String.join(",", gymIds.stream().map(String::valueOf).toArray(String[]::new)) + ")";
            try (Connection connection = DatabaseConfiguration.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    gyms.add(rs.getString("name"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return gyms;
    }

    public void changeMembershipPrices(GymMembershipRepository membershipRepository, String gymName, Integer percent) {
        String sql = "SELECT gymId FROM gyms WHERE name = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, gymName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int gymId = rs.getInt("gymId");
                membershipRepository.changeMembershipPricesOfSelectGym(gymId, percent);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Gym entity) {
        String sql = "UPDATE gyms SET name = ?, description = ?, capacity = ?, locationId = ? WHERE gymId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getDescription());
            stmt.setInt(3, entity.getCapacity());
            stmt.setInt(4, entity.getLocationId());
            stmt.setInt(5, entity.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Gym entity) {
        String sql = "DELETE FROM gyms WHERE gymId = ?";
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
        String sql = "SELECT COUNT(*) FROM gyms";
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
}
