package services;

import classes.Event;
import exceptions.InvalidDataException;
import repositories.EventRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class EventService {

    private EventRepository eventRepo = new EventRepository();

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