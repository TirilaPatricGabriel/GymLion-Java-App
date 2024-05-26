package classes;

import java.util.concurrent.atomic.AtomicInteger;
public class FitnessChallenge {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int id;

    private String name, description;
    private Integer points;

    public FitnessChallenge(){
        this.id = count.incrementAndGet();
    }

    public FitnessChallenge(Integer id, String name, String description, Integer points){
        this.id = id;
        this.name = name;
        this.description = description;
        this.points = points;
    }
    public FitnessChallenge(String name, String description, Integer points){
        this.id = count.incrementAndGet();
        this.name = name;
        this.description = description;
        this.points = points;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getPoints() {
        return this.points;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FitnessChallenge{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", points='" + getPoints() + '\'' + '}';
    }
}
