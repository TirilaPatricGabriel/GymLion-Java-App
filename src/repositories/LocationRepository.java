package repositories;

import classes.Customer;
import classes.Location;
import config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LocationRepository implements GenericRepository<Location> {

    private static Scanner scanner = new Scanner(System.in);
    private Location[] storage = new Location[100];

    @Override
    public void add(Location entity) throws SQLException {
        String sql = "CALL INSERT_LOCATION(?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConfiguration.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, entity.getCityName());
            stmt.setString(3, entity.getCountryName());
            stmt.setDouble(4, entity.getLatitude());
            stmt.setDouble(5, entity.getLongitude());
            stmt.execute();
        } catch (SQLException e) {
            throw new SQLException("Failed to add new location.", e);
        }
    }

    @Override
    public Location get(int id) throws SQLException {
        Location location = null;

        String locationSql = "SELECT * FROM locations WHERE locationId = ?";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement locationQuery = connection.prepareStatement(locationSql)) {
            locationQuery.setInt(1, id);
            ResultSet locations = locationQuery.executeQuery();

            if (locations.next()) {
                Integer locationId = locations.getInt("locationId");
                String cityName = locations.getString("cityName");
                String countryName = locations.getString("countryName");
                double latitude = locations.getDouble("latitude");
                double longitude = locations.getDouble("longitude");
                location = new Location(id, cityName, countryName, latitude, longitude);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get location.", e);
        }

        return location;
    }

    @Override
    public void update(Location entity) throws SQLException {
        String updateLocationSql = "UPDATE locations SET cityName = ?, countryName = ?, latitude = ?, longitude = ? WHERE locationId = ?";

        System.out.println("Country name:");
        String country = scanner.nextLine();
        System.out.println("City name:");
        String city = scanner.nextLine();
        System.out.println("Latitude:");
        double latitude = Double.parseDouble(scanner.nextLine());
        System.out.println("Longitude:");
        double longitude = Double.parseDouble(scanner.nextLine());

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement locationQuery = connection.prepareStatement(updateLocationSql)) {

            locationQuery.setString(1, city);
            locationQuery.setString(2, country);
            locationQuery.setDouble(3, latitude);
            locationQuery.setDouble(4, longitude);
            locationQuery.setDouble(5, entity.getId());
            locationQuery.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to update location.", e);
        }
    }

    @Override
    public void delete(Location entity) throws SQLException {
        String deleteLocationSql = "DELETE FROM locations WHERE locationId = ?";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement locationQuery = connection.prepareStatement(deleteLocationSql);
        ){
            locationQuery.setInt(1, entity.getId());
            locationQuery.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to delete location.", e);
        }
    }

    @Override
    public int getSize() {
        return 0;
    }

    public List<Location> getMostFrequentEventLocations() throws SQLException {
        String sql = "SELECT l.*, COUNT(e.eventId) as eventCount " +
                "FROM locations l " +
                "JOIN events e ON l.locationId = e.locationId " +
                "GROUP BY l.locationId " +
                "HAVING eventCount = (SELECT MAX(eventCount) FROM ( " +
                "    SELECT COUNT(e.eventId) as eventCount " +
                "    FROM locations l " +
                "    JOIN events e ON l.locationId = e.locationId " +
                "    GROUP BY l.locationId) as subquery) " +
                "ORDER BY eventCount DESC";

        List<Location> locations = new ArrayList<>();

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Location location = new Location();
                location.setId(rs.getInt("locationId"));
                location.setCityName(rs.getString("cityName"));
                location.setCountryName(rs.getString("countryName"));
                location.setLatitude(rs.getDouble("latitude"));
                location.setLongitude(rs.getDouble("longitude"));
                locations.add(location);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get most frequent locations.", e);
        }

        return locations;
    }


}
