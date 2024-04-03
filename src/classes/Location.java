package classes;

import java.util.concurrent.atomic.AtomicInteger;
public class Location {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int id;
    private String countryName, cityName;
    private double latitude, longitude;

    public Location(){
        this.id = count.incrementAndGet();
    }

    public Location(String countryName, String cityName, double latitude, double longitude) {
        this.id = count.incrementAndGet();
        this.countryName = countryName;
        this.cityName = cityName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return this.id;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public String getCityName() {
        return this.cityName;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
