package classes;

import java.util.concurrent.atomic.AtomicInteger;
public class Gym {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int id;
    private String name, description;
    private Integer capacity;
    private Integer locationId;

    public Gym(String name, String description, Integer capacity, Integer locationId) {
        this.id = count.incrementAndGet();
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.locationId = locationId;
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

    public Integer getLocationId() {
        return this.locationId;
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

    public void setLocation(Integer locationId) {
        this.locationId = locationId;
    }
}
