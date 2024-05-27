package services;

import classes.Customer;
import classes.Gym;
import classes.Location;
import exceptions.InvalidDataException;
import repositories.AthleteRepository;
import repositories.EventRepository;
import repositories.GymRepository;
import repositories.LocationRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationService {

    private LocationRepository locationRepo;

    private static LocationService instance;
    private AuditService auditService;

    private LocationService(LocationRepository repo) {
        this.locationRepo = repo;
        this.auditService = AuditService.getInstance();
    }

    public static LocationService getInstance(LocationRepository repo) {
        if (instance == null) {
            instance = new LocationService(repo);
        }
        return instance;
    }

    public void registerNewEntity(String countryName, String cityName, double latitude, double longitude) throws InvalidDataException, SQLException {
        if (countryName == null || countryName.trim().isEmpty()) {
            throw new IllegalArgumentException("Country name can't be empty");
        }
        if (cityName == null || cityName.trim().isEmpty()) {
            throw new IllegalArgumentException("City name can't be empty");
        }
        Location entity = new Location(countryName, cityName, latitude, longitude);
        locationRepo.add(entity);
    }

    public Location get(int index) throws InvalidDataException, SQLException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        return locationRepo.get(index);
    }

    public void update(int index) throws InvalidDataException, SQLException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        Location location = locationRepo.get(index);
        locationRepo.update(location);
    }

    public void delete(int index) throws InvalidDataException, SQLException {
        if (index < 0) {
            throw new InvalidDataException("Index can't be lower than 0");
        }
        Location location = locationRepo.get(index);
        locationRepo.delete(location);
    }

    public List<Location> getMostFrequentEventLocations() throws SQLException {
        auditService.logAction("Search for most frequent locations for events");
        return locationRepo.getMostFrequentEventLocations();
    }

}