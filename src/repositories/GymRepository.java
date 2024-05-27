package repositories;

import classes.Gym;
import config.DatabaseConfiguration;

import java.sql.*;
import java.util.*;

public class GymRepository implements GenericRepository<Gym> {

    private static Scanner scanner = new Scanner(System.in);

    @Override
    public void add(Gym entity) throws SQLException {
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
            throw new SQLException("Failed to add a new gym.", e);
        }
    }

    @Override
    public Gym get(int index) throws SQLException {
        Gym gym = null;
        String sql = "SELECT * FROM gyms WHERE gymId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, index);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Integer gymId = rs.getInt("gymId");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int capacity = rs.getInt("capacity");
                int locationId = rs.getInt("locationId");
                gym = new Gym(gymId, name, description, capacity, locationId);
                gym.setId(index);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get the gym.", e);
        }
        return gym;
    }

    public List<String> findGymsBasedOnMembershipPrices(Double startPrice, Double endPrice, GymMembershipRepository membershipRepository) throws SQLException {
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
                throw new SQLException("Failed to find gyms based on membership prices.", e);
            }
        }

        return gyms;
    }

    public void changeMembershipPrices(GymMembershipRepository membershipRepository, String gymName, Integer percent) throws SQLException {
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
            throw new SQLException("Failed to change membership prices.", e);
        }
    }

    @Override
    public void update(Gym entity) throws SQLException {
        String sql = "UPDATE gyms SET name = ?, description = ?, capacity = ?, locationId = ? WHERE gymId = ?";

        System.out.println("Enter new name:");
        String name = scanner.nextLine();
        System.out.println("Enter new description:");
        String description = scanner.nextLine();
        System.out.println("Enter new capacity:");
        int capacity = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter new location ID:");
        int locationId = Integer.parseInt(scanner.nextLine());

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setInt(3, capacity);
            stmt.setInt(4, locationId);
            stmt.setInt(5, entity.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to update gym.", e);
        }
    }

    @Override
    public void delete(Gym entity) throws SQLException {
        String sql = "DELETE FROM gyms WHERE gymId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, entity.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to delete gym.", e);
        }
    }

    @Override
    public int getSize() throws SQLException {
        String sql = "SELECT COUNT(*) FROM gyms";
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

    public List<Gym> findGymBasedOnMembershipPrices(double startPrice, double endPrice) throws SQLException {
        String sql = "SELECT g.* FROM gyms g, gym_memberships m, products p WHERE g.gymId = m.gymId AND m.productId = p.productId AND p.price BETWEEN ? AND ?";
        List<Gym> gyms = new ArrayList<>();

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setDouble(1, startPrice);
            stmt.setDouble(2, endPrice);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Gym gym = new Gym();
                    gym.setId(rs.getInt("gymId"));
                    gym.setName(rs.getString("name"));
                    gym.setDescription(rs.getString("description"));
                    gym.setLocation(rs.getInt("locationId"));
                    gym.setCapacity(rs.getInt("capacity"));
                    gyms.add(gym);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to find gyms.", e);
        }

        return gyms;
    }

    public void changeMembershipPrices(String gymName, Integer percent) throws SQLException {
        String sql = "UPDATE products p JOIN gym_memberships gm ON gm.productId = p.productId SET p.price = p.price + (p.price * ? / 100) WHERE p.productId = gm.productId AND gm.gymId = (SELECT gymId FROM gyms WHERE name = ?)";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, percent);
            stmt.setString(2, gymName);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to change membership prices.", e);
        }
    }

    public Map<String, Integer> getGymMembershipCounts() throws SQLException {
        String query = "SELECT g.name, COUNT(DISTINCT gm.membershipId) AS membershipCount " +
                "FROM gyms g " +
                "LEFT JOIN gym_memberships gm ON g.gymId = gm.gymId " +
                "GROUP BY g.name";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery())
        {

            Map<String, Integer> gymMembershipCounts = new HashMap<>();

            while (rs.next()) {
                String gymName = rs.getString("name");
                int membershipCount = rs.getInt("membershipCount");
                gymMembershipCounts.put(gymName, membershipCount);
            }

            return gymMembershipCounts;
        } catch (SQLException e) {
            throw new SQLException("Failed to get gym membership counts.", e);
        }
    }
}
