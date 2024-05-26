package classes;

public class Athlete extends Person {
    private double salary, bonusPerTenThousandLikes;
    private int socialMediaFollowers;

    public Athlete(Integer id, String name, String email, String phone, String address, int age, double salary, int socialMediaFollowers, double bonusPerTenThousandLikes) {
        super(id, name, email, phone, address, age);
        this.salary = salary;
        this.socialMediaFollowers = socialMediaFollowers;
        this.bonusPerTenThousandLikes = bonusPerTenThousandLikes;
    }
    public Athlete(String name, String email, String phone, String address, int age, double salary, int socialMediaFollowers, double bonusPerTenThousandLikes) {
        super(name, email, phone, address, age);
        this.salary = salary;
        this.socialMediaFollowers = socialMediaFollowers;
        this.bonusPerTenThousandLikes = bonusPerTenThousandLikes;
    }

    public double calculateTotalEarnings(int totalLikes) {
        int bonusMultiplier = totalLikes / 10000;
        return salary + (bonusMultiplier * bonusPerTenThousandLikes);
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Salary: $" + salary);
        System.out.println("Social Media Followers: " + socialMediaFollowers);
        System.out.println("Bonus per 10,000 Likes: $" + bonusPerTenThousandLikes);
    }

    // Getters
    public double getSalary() {
        return this.salary;
    }

    public int getSocialMediaFollowers() {
        return this.socialMediaFollowers;
    }

    public double getBonusPerTenThousandLikes() {
        return this.bonusPerTenThousandLikes;
    }

    // Setters
    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setSocialMediaFollowers(int socialMediaFollowers) {
        this.socialMediaFollowers = socialMediaFollowers;
    }

    public void setBonusPerTenThousandLikes(double bonusPerTenThousandLikes) {
        this.bonusPerTenThousandLikes = bonusPerTenThousandLikes;
    }

    @Override
    public String toString() {
        return "Athlete{" +
                "name='" + getName() + '\'' +
                ", email='" + super.getEmail() + '\'' +
                ", phone='" + super.getPhone() + '\'' +
                ", address='" + super.getAddress() + '\'' +
                ", age=" + getAge() +
                ", salary=" + getSalary() +
                ", socialMediaFollowers=" + getSocialMediaFollowers() +
                ", bonusPerTenThousandLikes=" + getBonusPerTenThousandLikes() +
                '}';
    }

    public void setId(int athleteId) {
        super.setId(athleteId);
    }
}
