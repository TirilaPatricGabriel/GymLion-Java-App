package classes;

import java.util.concurrent.atomic.AtomicInteger;

public class GymMembership extends Product  {
    private Integer gymId;
    private Integer durationInMonths;

    public GymMembership(Integer gymId, double price, Integer durationInMonths, String code) {
        super(price, code);
        this.gymId = gymId;
        this.durationInMonths = durationInMonths;
    }

    public Integer getGymId() {
        return gymId;
    }

    public Integer getDurationInMonths() {
        return durationInMonths;
    }

    public void setGymId(Integer gymId) {
        this.gymId = gymId;
    }

    public void setDurationInMonths(Integer durationInMonths) {
        this.durationInMonths = durationInMonths;
    }

    public double getPrice() {
        return super.getPrice();
    }

}
