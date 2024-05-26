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

    public void registerNewEntity(LocalDate startDate, LocalDate endDate, String name, String description, Integer capacity, Integer locationId) throws InvalidDataException {
        Event entity = new Event(startDate, endDate, name, description, capacity, locationId);
        eventRepo.add(entity);
    }

    public Event get(int index) throws InvalidDataException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        return eventRepo.get(index);
    }

    public void update(int index) throws InvalidDataException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        Event event = eventRepo.get(index);
        eventRepo.update(event);
    }

    public void delete(int index) throws InvalidDataException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        Event event = eventRepo.get(index);
        eventRepo.delete(event);
    }

    public void deleteOldEvents() {
        eventRepo.deleteOldEvents();
        auditService.logAction("Deleted old events");
    }

    public List<Event> getAllEventsFromAPeriodAndACity(Date startDate, Date endDate, String city) {
        auditService.logAction("Searched for events within a period from a city");
        return eventRepo.getAllEventsFromAPeriodAndACity(startDate, endDate, city);
    }
}