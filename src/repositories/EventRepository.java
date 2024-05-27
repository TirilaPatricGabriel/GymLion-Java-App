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
    public void add(Event entity) throws SQLException {
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
            throw new SQLException("Failed to add new event.", e);
        }
    }

    @Override
    public Event get(int id) throws SQLException {
        Event event = null;

        String eventSql = "SELECT * FROM events WHERE eventId = ?";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement eventQuery = connection.prepareStatement(eventSql)) {
            eventQuery.setInt(1, id);
            ResultSet events = eventQuery.executeQuery();

            if (events.next()) {
                Integer eventId = events.getInt("eventId");
                Date startDate = events.getDate("startDate");
                Date endDate = events.getDate("endDate");
                String name = events.getString("name");
                String description = events.getString("description");
                Integer capacity = events.getInt("capacity");
                Integer locationId = events.getInt("locationId");

                event = new Event(eventId, startDate.toLocalDate(), endDate.toLocalDate(), name, description, capacity, locationId);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get event.", e);
        }

        return event;
    }

    @Override
    public void update(Event entity) throws SQLException {
        String updateEventSql = "UPDATE events SET startDate = ?, endDate = ?, name = ?, description = ?, capacity = ?, locationId = ? WHERE eventId = ?";

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
            eventQuery.setInt(7, entity.getId());
            eventQuery.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to update event.", e);
        }
    }

    @Override
    public void delete(Event entity) throws SQLException {
        String deleteEventSql = "DELETE FROM events WHERE eventId = ?";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement eventQuery = connection.prepareStatement(deleteEventSql);
        ){
            eventQuery.setInt(1, entity.getId());
            eventQuery.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to delete event.", e);
        }
    }

    public void deleteOldEvents() throws SQLException {
        String sql = "DELETE FROM events WHERE endDate < ?";

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to delete old events.", e);
        }
    }

    @Override
    public int getSize() {
        return storage.length;
    }

    public List<Event> getAllEventsFromAPeriodAndACity(Date startDate, Date endDate, String city) throws SQLException {
        String sql = "SELECT e.* FROM events e " +
                "JOIN locations l ON e.locationId = l.locationId " +
                "WHERE l.cityName = ? AND e.startDate >= ? AND e.endDate <= ?";

        List<Event> events = new ArrayList<>();

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, city);
            stmt.setDate(2, startDate);
            stmt.setDate(3, endDate);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Event event = new Event();
                    event.setId(rs.getInt("eventId"));
                    event.setName(rs.getString("name"));
                    event.setDescription(rs.getString("description"));
                    event.setStartDate(rs.getDate("startDate"));
                    event.setEndDate(rs.getDate("endDate"));
                    event.setLocation(rs.getInt("locationId"));
                    event.setCapacity(rs.getInt("capacity"));

                    events.add(event);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get all events.", e);
        }

        return events;
    }
}
