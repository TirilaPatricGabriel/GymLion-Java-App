package services;

import classes.Customer;
import classes.Gym;
import classes.Location;
import exceptions.InvalidDataException;
import repositories.EventRepository;
import repositories.GymRepository;
import repositories.LocationRepository;

import java.util.ArrayList;

public class LocationService {

    private LocationRepository locationRepo = new LocationRepository();

    public LocationService(LocationRepository repo) {
        this.locationRepo = repo;
    }

    public void registerNewEntity(String countryName, String cityName, double latitude, double longitude) throws InvalidDataException {
        Location entity = new Location(countryName, cityName, latitude, longitude);
        locationRepo.add(entity);
    }

    public Location get(int index) throws InvalidDataException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        return locationRepo.get(index);
    }

    public void update(int index) throws InvalidDataException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        Location location = locationRepo.get(index);
        locationRepo.update(location);
    }

    public void delete(int index) throws InvalidDataException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        Location location = locationRepo.get(index);
        locationRepo.delete(location);
    }

//    public ArrayList<Location> mostFrequentLocations(EventRepository eventRepo) {
//        return locationRepo.mostFrequentLocations(eventRepo);
//    }
}