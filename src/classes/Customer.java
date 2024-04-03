package classes;

public class Customer extends Person {
    double balance;

    // Constructor
    public Customer(String name, String email, String phone, String address, double balance) {
        super(name, email, phone, address);
        this.balance = balance;
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
}
