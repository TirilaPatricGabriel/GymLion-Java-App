package classes;

class Person {
    private String name, email, phone, address;

    public Person(String name, String email, String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public void displayInfo() {
        System.out.println("Name: " + this.name);
        System.out.println("Email: " + this.email);
        System.out.println("Phone: " + this.phone);
        System.out.println("Address: " + this.address);
    }

    // Getters
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
}

