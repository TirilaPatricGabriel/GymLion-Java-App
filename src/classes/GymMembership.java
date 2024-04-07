package classes;

import java.time.LocalDate;

public class GymMembership {
    private Integer gymId;
    private double price;
    private Integer durationInMonths;
    private String code;

    public GymMembership(Integer gymId, double price, Integer durationInMonths, String code) {
        this.gymId = gymId;
        this.price = price;
        this.durationInMonths = durationInMonths;
        this.code = code;
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

    public String getCode() {
        return code;
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

    public void setCode(String code) {
        this.code = code;
    }
}
