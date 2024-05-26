package classes;

import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
public class Event {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int id;
    private Date startDate, endDate;
    private String name, description;
    private Integer capacity;
    private Integer locationId;

    public Event(){
        this.id = count.incrementAndGet();
    }

    public Event(Integer id, LocalDate startDate, LocalDate endDate, String name, String description, Integer capacity, Integer locationId) {
        this.id = id;
        this.startDate = Date.valueOf(startDate);
        this.endDate = Date.valueOf(endDate);
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.locationId = locationId;
    }
    public Event(LocalDate startDate, LocalDate endDate, String name, String description, Integer capacity, Integer locationId) {
        this.id = count.incrementAndGet();
        this.startDate = Date.valueOf(startDate);
        this.endDate = Date.valueOf(endDate);
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.locationId = locationId;
    }

    public int getId() {
        return this.id;
    }

    public LocalDate getStartDate() {
        return this.startDate.toLocalDate();
    }
    public LocalDate getEndDate() {
        return this.endDate.toLocalDate();
    }

    public Integer getLocationId() {
        return this.locationId;
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

    public void setLocation(Integer locationId) {
        this.locationId = locationId;
    }

    public void setMyDate(LocalDate startDate) {
        this.startDate = Date.valueOf(startDate);
    }

    public void setId(Integer eventId) {
        this.id = eventId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + getName() + '\'' +
                ", desc='" + getDescription() + '\'' +
                ", startDate='" + getStartDate() + '\'' +
                ", endDate='" + getEndDate() + '\'' +
                ", capacity=" + getCapacity() +
                '}';
    }
}
