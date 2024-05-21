package classes;

import java.util.concurrent.atomic.AtomicInteger;
public class Person implements Comparable<Person>{
    private static final AtomicInteger count = new AtomicInteger(0);
    private int id;
    private String name, email, phone, address;
    private int age;


    public Person(String name, String email, String phone, String address, int age) {
        this.id = count.incrementAndGet();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.age = age;
    }

    public void displayInfo() {
        System.out.println("Name: " + this.name);
        System.out.println("Email: " + this.email);
        System.out.println("Phone: " + this.phone);
        System.out.println("Address: " + this.address);
    }

    @Override
    public int compareTo(Person other) {
        return Integer.compare(this.age, other.getAge());
    }

    // Getters
    public int getId() {
        return this.id;
    }

    public int getAge() {
        return age;
    }

    public String getName () {
        return this.name;
    }

    public String getEmail () {
        return this.email;
    }

    public String getPhone () {
        return this.phone;
    }

    public String getAddress () {
        return this.address;
    }

    // Setters
    public void setName (String name) {
        this.name = name;
    }

    public void setEmail (String name) {
        this.name = name;
    }

    public void setPhone (String name) {
        this.name = name;
    }

    public void setAddress (String name) {
        this.name = name;
    }

    protected void setId(int id) {
        this.id = id;
    }
}

