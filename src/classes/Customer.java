package classes;

import java.util.ArrayList;
public class Customer extends Person {
    private double balance;
//    private ArrayList<FitnessChallenge> challengesCompleted = new ArrayList<>();

    // Constructor
    public Customer(String name, String email, String phone, String address, int age, double balance) {
        super(name, email, phone, address, age);
        this.balance = balance;
//        this.challengesCompleted.addAll(challengesCompleted);
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Balance: $" + balance);
    }

    public double getBalance() {
        return this.balance;
    }
//    public ArrayList<FitnessChallenge> getChallengesCompleted() {
//        return this.challengesCompleted;
//    }
//    public ArrayList<Integer> getChallengesCompletedIds() {
//        ArrayList<Integer> challengesCompletedIds = new ArrayList<>();
//        for (int i=0; i<this.challengesCompleted.size(); i++) {
//            if (challengesCompleted.get(i) != null) {
//                challengesCompletedIds.add(challengesCompleted.get(i).getId());
//            }
//        }
//        return challengesCompletedIds;
//    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", balance=" + balance +
                '}';
    }
}
