package classes;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
public class Gym {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int id;
    private String name, description;
    private Integer capacity;
    private Location location;

    public Gym(){
        this.id = count.incrementAndGet();
    }

    public Gym(String name, String description, Integer capacity, Location location) {
        this.id = count.incrementAndGet();
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.location.setLatitude(location.getLatitude());
        this.location.setLongitude(location.getLongitude());
        this.location.setCountryName(location.getCountryName());
        this.location.setCityName(location.getCityName());
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getCapacity() {
        return this.capacity;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
