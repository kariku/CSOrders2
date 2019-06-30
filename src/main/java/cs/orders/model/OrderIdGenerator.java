package cs.orders.model;

import java.util.concurrent.atomic.AtomicLong;

public class OrderIdGenerator {
    private final AtomicLong id = new AtomicLong();


    public String getNextId() {
        long val = id.incrementAndGet();
        return String.valueOf(val);
    }
}
