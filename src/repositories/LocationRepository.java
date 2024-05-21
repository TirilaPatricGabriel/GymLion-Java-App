package repositories;

import classes.Customer;
import classes.Location;
import config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Repositories are responsible for interacting with the storage of entities. Usually a 1-to-1 relation with the
 * entities. Any entity that should be persisted, should have a Repo.
 * */
public class LocationRepository implements GenericRepository<Location> {

    private Location[] storage = new Location[100];

    @Override
    public void add(Location entity) {
        String sql = "CALL INSERT_LOCATION(?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConfiguration.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, entity.getCountryName());
            stmt.setString(3, entity.getCityName());
            stmt.setDouble(4, entity.getLatitude());
            stmt.setDouble(5, entity.getLongitude());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Location get(int id) {
        Location location = null;

        String locationSql = "SELECT * FROM locations WHERE locationId = ?";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement locationQuery = connection.prepareStatement(locationSql)) {
            locationQuery.setInt(1, id);
            ResultSet locations = locationQuery.executeQuery();

            if (locations.next()) {
                String cityName = locations.getString("cityName");
                String countryName = locations.getString("countryName");
                double latitude = locations.getDouble("latitude");
                double longitude = locations.getDouble("longitute");
                location = new Location(cityName, countryName, latitude, longitude);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return location;
    }

    @Override
    public void update(Location entity) {
        String updateLocationSql = "UPDATE locations SET cityName = ?, countryName = ?, latitude = ?, longitude = ? WHERE locationId = ?";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement locationQuery = connection.prepareStatement(updateLocationSql)) {

            locationQuery.setString(1, entity.getCityName());
            locationQuery.setString(2, entity.getCountryName());
            locationQuery.setDouble(3, entity.getLatitude());
            locationQuery.setDouble(4, entity.getLongitude());
            locationQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Location entity) {
        String deleteLocationSql = "DELETE FROM locations WHERE locationId = ?";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement locationQuery = connection.prepareStatement(deleteLocationSql);
        ){
            locationQuery.setInt(1, entity.getId());
            locationQuery.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getSize() {
        return 0;
    }


//    @Override
//    public void add(Location entity) {
//        for (int i=0; i<storage.length; i++) {
//            if (storage[i] == null) {
//                storage[i] = entity;
//                return;
//            }
//        }
//        Location[] newStorage = Arrays.<Location, Location>copyOf(storage, 2*storage.length, Location[].class);
//        newStorage[storage.length] = entity;
//        storage = newStorage;
//    }
//
//    @Override
//    public Location get(int index) {
//        return storage[index];
//    }
//
//    @Override
//    public void update(Location entity) {
//        for (int i=0; i<storage.length; i++) {
//            if (storage[i] == entity) {
//                // TODO UPDATE
//            }
//        }
//    }
//
//    @Override
//    public void delete(Location entity) {
//        for (int i = 0; i < storage.length; i++) {
//            if (storage[i] != null && storage[i] == entity) {
//                storage[i] = null;
//                break;
//            }
//        }
//    }
//
//    @Override
//    public int getSize() {
//        return storage.length;
//    }

    public ArrayList<Location> mostFrequentLocations(EventRepository eventRepo) {
        Integer max1 = 0, max2 = 0, max3 = 0;
        Location l1 = null, l2 = null, l3 = null;
        for (int i=0; i<storage.length; i++) {
            if (storage[i] != null) {
                Integer nr = eventRepo.getNumberOfEventsFromLocation(storage[i].getId());
                System.out.println("nr: " + nr);
                if (nr > max1) {
                    if (max1 > max2) {
                        if (max2 > max3) {
                            max3 = max2;
                            l3 = l2;
                        }
                        max2 = max1;
                        l2 = l1;
                    }
                    max1 = nr;
                    l1 = storage[i];
                } else if (nr > max2) {
                    if (max2 > max3) {
                        max3 = max2;
                        l3 = l2;
                    }
                    max2 = nr;
                    l2 = storage[i];
                } else if (nr > max3) {
                    max3 = nr;
                    l3 = storage[i];
                }
            }
        }
        ArrayList<Location> res = new ArrayList<>();
        res.add(l1);
        res.add(l2);
        res.add(l3);
        return res;
    }
}
