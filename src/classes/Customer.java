package classes;

import java.util.ArrayList;
public class Customer extends Person {
    private double balance;

    public Customer(Integer id, String name, String email, String phone, String address, int age, double balance) {
        super(id, name, email, phone, address, age);
        this.balance = balance;
    }

    public Customer(String name, String email, String phone, String address, int age, double balance) {
        super(name, email, phone, address, age);
        this.balance = balance;
    }

    public Customer() {
        super();
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Balance: $" + balance);
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setName (String name) {
        super.setName(name);
    }

    public void setAddress (String address) {
        super.setAddress(address);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + super.getName() + '\'' +
                ", age=" + super.getAge() +
                ", balance=" + getBalance() +
                '}';
    }

}
