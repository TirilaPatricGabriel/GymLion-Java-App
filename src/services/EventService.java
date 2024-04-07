package services;

import classes.Athlete;
import classes.Customer;
import classes.Event;
import classes.Location;
import exceptions.InvalidDataException;
import repositories.EventRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class EventService {
    private EventRepository eventRepo = new EventRepository();

    public void registerNewEntity(LocalDate startDate, LocalDate endDate, String name, String description, Integer capacity, Location location) throws InvalidDataException {
        Event entity = new Event(startDate, endDate, name, description, capacity, location);
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

    public ArrayList<HashMap<String, String>> getAllEventsFromAPlaceWithinAPeriod (LocalDate startDate, LocalDate endDate, String countryName) throws InvalidDataException {
        if (startDate == null || endDate == null || startDate.compareTo(endDate) > 0) {
            throw new InvalidDataException("Invalid dates");
        }
        return eventRepo.getAllEventsFromAPlaceWithinAPeriod(startDate, endDate, countryName);
    }

    public void deleteAllEventsThatAlreadyTookPlace () {
        eventRepo.deleteAllEventsThatAlreadyTookPlace();
        System.out.println("All events that took place were deleted successfully!");
    }
}