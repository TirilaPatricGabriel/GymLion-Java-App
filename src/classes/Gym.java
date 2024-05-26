package classes;

import java.util.concurrent.atomic.AtomicInteger;
public class Gym {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int id;
    private String name, description;
    private Integer capacity;
    private Integer locationId;

    public Gym(Integer id, String name, String description, Integer capacity, Integer locationId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.locationId = locationId;
    }

    public Gym(String name, String description, Integer capacity, Integer locationId) {
        this.id = count.incrementAndGet();
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.locationId = locationId;
    }

    public Gym() {
        this.id = count.incrementAndGet();
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

    public void setId(int id) {
        this.id = id;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "Gym{" +
                "Name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", capacity=" + getCapacity() +
                ", locationId=" + getLocationId() +
                '}';
    }
}
