package services;

import classes.Athlete;
import classes.Customer;
import classes.Event;
import classes.Location;
import exceptions.InvalidDataException;
import repositories.AthleteRepository;
import repositories.EventRepository;
import repositories.GymRepository;
import repositories.LocationRepository;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class EventService {
    private EventRepository eventRepo;

    private static EventService instance;
    private AuditService auditService;

    private EventService(EventRepository repo) {
        this.eventRepo = repo;
        this.auditService = AuditService.getInstance();
    }

    public static EventService getInstance(EventRepository repo) {
        if (instance == null) {
            instance = new EventService(repo);
        }
        return instance;
    }

    public void registerNewEntity(LocalDate startDate, LocalDate endDate, String name, String description, Integer capacity, Integer locationId) throws InvalidDataException, SQLException {
        Event entity = new Event(startDate, endDate, name, description, capacity, locationId);
        eventRepo.add(entity);
    }

    public Event get(int index) throws InvalidDataException, SQLException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        return eventRepo.get(index);
    }

    public void update(int index) throws InvalidDataException, SQLException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        Event event = eventRepo.get(index);
        eventRepo.update(event);
    }

    public void delete(int index) throws InvalidDataException, SQLException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        Event event = eventRepo.get(index);
        eventRepo.delete(event);
    }

    public void deleteOldEvents() throws SQLException {
        eventRepo.deleteOldEvents();
        auditService.logAction("Deleted old events");
    }

    public List<Event> getAllEventsFromAPeriodAndACity(Date startDate, Date endDate, String city) throws SQLException, InvalidDataException {
        int comparisonResult = startDate.compareTo(endDate);

        if (comparisonResult > 0) {
            throw new InvalidDataException("Starting date can't be after end date");
        }
        if (Objects.equals(city, "")) {
            throw new InvalidDataException("No city name given");
        }

        auditService.logAction("Searched for events within a period from a city");
        return eventRepo.getAllEventsFromAPeriodAndACity(startDate, endDate, city);
    }
}