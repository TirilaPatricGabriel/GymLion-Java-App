package classes;

import java.util.concurrent.atomic.AtomicInteger;

public class Product {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int id;
    private double price;
    private String code;

    public Product(double price, String code) {
        this.id = count.incrementAndGet();
        this.price = price;
        this.code = code;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getPrice() {
        return price;
    }

    public String getCode() {
        return code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "code=" + getCode() +
                ", price=" + getPrice() +
                '}';
    }
}