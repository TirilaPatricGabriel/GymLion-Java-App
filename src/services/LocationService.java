package services;

import classes.Location;
import exceptions.InvalidDataException;
import repositories.EventRepository;
import repositories.LocationRepository;

import java.util.ArrayList;

public class LocationService {

    private LocationRepository locationRepo = new LocationRepository();

    public ArrayList<Location> mostFrequentLocations(EventRepository eventRepo) {
        return locationRepo.mostFrequentLocations(eventRepo);
    }
}