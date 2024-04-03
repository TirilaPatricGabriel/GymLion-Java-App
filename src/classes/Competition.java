package classes;

import java.util.concurrent.atomic.AtomicInteger;
public class Competition {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int id;

    public Competition(){
        this.id = count.incrementAndGet();
    }

    public int getId() {
        return this.id;
    }
}
