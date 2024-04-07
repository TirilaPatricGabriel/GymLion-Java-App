package classes;

import java.util.ArrayList;
public class Customer extends Person {
    private double balance;
    private ArrayList<FitnessChallenge> challengesCompleted;

    // Constructor
    public Customer(String name, String email, String phone, String address, double balance, ArrayList<FitnessChallenge> challengesCompleted) {
        super(name, email, phone, address);
        this.balance = balance;
        this.challengesCompleted.addAll(challengesCompleted);
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Balance: $" + balance);
    }

    public double getBalance() {
        return this.balance;
    }
    public ArrayList<FitnessChallenge> getChallengesCompleted() {
        return this.challengesCompleted;
    }
    public ArrayList<Integer> getChallengesCompletedIds() {
        ArrayList<Integer> challengesCompletedIds = null;
        for (int i=0; i<this.challengesCompleted.size(); i++) {
            if (challengesCompleted.get(i) != null) {
                challengesCompletedIds.add(challengesCompleted.get(i).getId());
            }
        }
        return challengesCompletedIds;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void addChallengeToCompleted (FitnessChallenge challenge) {
        FitnessChallenge newChallenge = challenge;
        this.challengesCompleted.add(newChallenge);
    }
}
