package classes;

import java.util.concurrent.atomic.AtomicInteger;
public class Investor {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int id;
    private String name;
    private double contractValue;

    public Investor(){
        this.id = count.incrementAndGet();
    }
    public Investor(String name, double contractValue){
        this.id = count.incrementAndGet();
        this.name = name;
        this.contractValue = contractValue;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public double getContractValue() {
        return contractValue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContractValue(double contractValue) {
        this.contractValue = contractValue;
    }
}
