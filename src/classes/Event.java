package classes;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
public class Event {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int id;
    private LocalDate startDate, endDate;
    private String name, description;
    private Integer capacity;
    private Location location;

    public Event(){
        this.id = count.incrementAndGet();
    }
    public Event(LocalDate startDate, LocalDate endDate, String name, String description, Integer capacity, Location location) {
        this.id = count.incrementAndGet();
        Location newLocation = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.location.setLatitude(newLocation.getLatitude());
        this.location.setLongitude(newLocation.getLongitude());
        this.location.setCountryName(newLocation.getCountryName());
        this.location.setCityName(newLocation.getCityName());
    }

    public int getId() {
        return this.id;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }
    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Location getLocation() {
        return this.location;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setMyDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
