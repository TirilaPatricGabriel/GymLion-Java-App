package repositories;

import classes.Event;
import classes.Location;
import config.DatabaseConfiguration;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.time.LocalDate;


/**
 * Repositories are responsible for interacting with the storage of entities. Usually a 1-to-1 relation with the
 * entities. Any entity that should be persisted, should have a Repo.
 * */
public class EventRepository implements GenericRepository<Event> {

    private static Scanner scanner = new Scanner(System.in);
    private Event[] storage = new Event[100];


    @Override
    public void add(Event entity) {
        String sql = "CALL INSERT_EVENT(?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConfiguration.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setDate(2, Date.valueOf(entity.getStartDate()));
            stmt.setDate(3, Date.valueOf(entity.getEndDate()));
            stmt.setString(4, entity.getName());
            stmt.setString(5, entity.getDescription());
            stmt.setInt(6, entity.getCapacity());
            stmt.setInt(7, entity.getLocationId());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Event get(int id) {
        Event event = null;

        String eventSql = "SELECT * FROM events WHERE eventId = ?";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement eventQuery = connection.prepareStatement(eventSql)) {
            eventQuery.setInt(1, id);
            ResultSet events = eventQuery.executeQuery();

            if (events.next()) {
                Date startDate = events.getDate("startDate");
                Date endDate = events.getDate("endDate");
                String name = events.getString("name");
                String description = events.getString("description");
                Integer capacity = events.getInt("capacity");
                Integer locationId = events.getInt("locationId");

                event = new Event(startDate.toLocalDate(), endDate.toLocalDate(), name, description, capacity, locationId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return event;
    }

    @Override
    public void update(Event entity) {
        String updateEventSql = "UPDATE events SET startDate = ?, endDate = ?, name = ?, description = ?, capacity = ?, locationId = ? WHERE locationId = ?";

        System.out.println("Start date:");
        Date startDate = Date.valueOf(scanner.nextLine());
        System.out.println("End date:");
        Date endDate = Date.valueOf(scanner.nextLine());
        System.out.println("Name:");
        String name = scanner.nextLine();
        System.out.println("Description:");
        String description = scanner.nextLine();
        System.out.println("Capacity:");
        Integer capacity = Integer.parseInt(scanner.nextLine());
        System.out.println("Location ID:");
        Integer locationId = Integer.parseInt(scanner.nextLine());

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement eventQuery = connection.prepareStatement(updateEventSql)) {

            eventQuery.setDate(1, startDate);
            eventQuery.setDate(2, endDate);
            eventQuery.setString(3, name);
            eventQuery.setString(4, description);
            eventQuery.setInt(5, capacity);
            eventQuery.setInt(6, locationId);
            eventQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Event entity) {
        String deleteEventSql = "DELETE FROM events WHERE eventId = ?";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement eventQuery = connection.prepareStatement(deleteEventSql);
        ){
            eventQuery.setInt(1, entity.getId());
            eventQuery.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public List<HashMap<String, String>> getAllEventsFromAPlaceWithinAPeriod(LocalDate startDate, LocalDate endDate, String countryName, LocationRepository locRepo) throws SQLException {
//        List<HashMap<String, String>> res = new ArrayList<>();
//        String sql = "SELECT e.name, e.description, e.startDate, e.endDate, l.countryName FROM events e JOIN locations l ON e.locationId = l.locationId WHERE l.countryName = ? AND e.startDate >= ? AND e.endDate <= ?";
//
//        try (Connection connection = DatabaseConfiguration.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setString(1, countryName);
//            stmt.setDate(2, Date.valueOf(startDate));
//            stmt.setDate(3, Date.valueOf(endDate));
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                HashMap<String, String> obj = new HashMap<>();
//                obj.put("name", rs.getString("name"));
//                obj.put("description", rs.getString("description"));
//                res.add(obj);
//            }
//        }
//        return res;
//    }
//
//    public void deleteAllEventsThatAlreadyTookPlace() throws SQLException {
//        String sql = "DELETE FROM events WHERE endDate < ?";
//        try (Connection connection = DatabaseConfiguration.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setDate(1, Date.valueOf(LocalDate.now()));
//            stmt.executeUpdate();
//        }
//    }
































//    @Override
//    public void add(Event entity) {
//        for (int i=0; i<storage.length; i++) {
//            if (storage[i] == null) {
//                storage[i] = entity;
//                return;
//            }
//        }
//        Event[] newStorage = Arrays.<Event, Event>copyOf(storage, 2*storage.length, Event[].class);
//        newStorage[storage.length] = entity;
//        storage = newStorage;
//    }
//
//    @Override
//    public Event get(int index) {
//        return storage[index];
//    }
//
//    @Override
//    public void update(Event entity) {
//        for (int i=0; i<storage.length; i++) {
//            if (storage[i] == entity) {
//                // TODO UPDATE
//            }
//        }
//    }
//
//    @Override
//    public void delete(Event entity) {
//        for (int i = 0; i < storage.length; i++) {
//            if (storage[i] != null && storage[i] == entity) {
//                storage[i] = null;
//                break;
//            }
//        }
//    }
//
    @Override
    public int getSize() {
        return storage.length;
    }

//    public ArrayList<HashMap<String, String>> getAllEventsFromAPlaceWithinAPeriod (LocalDate startDate, LocalDate endDate, String countryName, LocationRepository locRepo) {
//        ArrayList<HashMap<String, String>> res = new ArrayList<>();
//        for (int i = 0; i < storage.length; i++) {
//            if (storage[i] != null && storage[i].getStartDate().compareTo(startDate) >= 0 && storage[i].getEndDate().compareTo(endDate) <= 0 && Objects.equals(locRepo.get(storage[i].getLocationId()).getCountryName(), countryName)) {
//                HashMap<String, String> obj = new HashMap<>();
//                obj.put("name", storage[i].getName());
//                obj.put("description", storage[i].getDescription());
//                res.add(obj);
//                break;
//            }
//        }
//        return res;
//    }
//
//    public void deleteAllEventsThatAlreadyTookPlace () {
//        for (int i=0; i<storage.length; i++) {
//            if (storage[i] != null && storage[i].getEndDate().compareTo(LocalDate.now()) < 0) {
//                System.out.println(storage[i].getEndDate());
//                System.out.println(LocalDate.now());
//                storage[i] = null;
//            }
//        }
//    }
//
//    public Integer getNumberOfEventsFromLocation(Integer locId) {
//        Integer nr = 0;
//        System.out.println("loc id:" + locId);
//        for (int i=0; i<storage.length; i++) {
//            if (storage[i] != null) {
//                System.out.println("id: " + storage[i].getLocationId());
//            }
//            if (storage[i] != null && storage[i].getLocationId() == locId) {
//                nr += 1;
//            }
//        }
//        return nr;
//    }
}
