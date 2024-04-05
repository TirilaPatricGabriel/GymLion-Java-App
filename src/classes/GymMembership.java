package classes;

import java.time.LocalDate;

public class GymMembership {
    private Integer gymId;
    private double price;
    private Integer durationInMonths;

    public GymMembership(Integer gymId, double price, Integer durationInMonths) {
        this.gymId = gymId;
        this.price = price;
        this.durationInMonths = durationInMonths;
    }

    public Integer getGymId() {
        return gymId;
    }

    public double getPrice() {
        return price;
    }

    public Integer getDurationInMonths() {
        return durationInMonths;
    }

    public void setGymId(Integer gymId) {
        this.gymId = gymId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDurationInMonths(Integer durationInMonths) {
        this.durationInMonths = durationInMonths;
    }
}
