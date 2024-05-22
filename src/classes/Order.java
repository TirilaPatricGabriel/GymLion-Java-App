package classes;

import java.time.LocalDate;

public class Order {
    private int id;
    private int customerId;
    private LocalDate date;
    private double price;

    public Order(int customerId, LocalDate date, double price) {
        this.customerId = customerId;
        this.date = date;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "date='" + getDate() + '\'' +
                ", price='" + getPrice() + '\'' +
                '}';
    }
}
