package classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Order {
    private int customerId;
    private LocalDate date;
//    private ArrayList<String> codes;
    private double price;

    public Order(int customerId, LocalDate date, double price) {
        this.customerId = customerId;
        this.date = date;
//        this.codes = codes;
        this.price = price;
    }

    public int getCustomerId () {
        return this.customerId;
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

    public void setPrice(double codes) {
        this.price = codes;
    }
}
