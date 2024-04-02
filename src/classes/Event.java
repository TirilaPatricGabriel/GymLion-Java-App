package classes;

import java.time.LocalDate;
public class Event {
    private LocalDate startDate; // Declare a LocalDate field

    // Constructor
    public Event(LocalDate startDate) {
        this.startDate = startDate;
    }

    // Getter and setter methods (if needed)
    public LocalDate getMyDate() {
        return this.startDate;
    }

    public void setMyDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
