package classes;

import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
public class Event {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int id;
    private LocalDate startDate, endDate;
    private String name, description;
    private Integer capacity;
    private Integer locationId;

    public Event(){
        this.id = count.incrementAndGet();
    }
    public Event(LocalDate startDate, LocalDate endDate, String name, String description, Integer capacity, Integer locationId) {
        this.id = count.incrementAndGet();
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.locationId = locationId;
    }

    public int getId() {
        return this.id;
    }

    public Date getStartDate() {
        return this.startDate;
    }
    public Date getEndDate() {
        return this.endDate;
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
        this.startDate = startDate;
    }
}
