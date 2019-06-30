package cs.orders;

import java.util.Collection;
import java.util.LinkedList;

public class OrderBook {
    private final Collection<Order> orders = new LinkedList<>();
    private volatile boolean open = true;   // decided to use basic synchronization, to keep things simple
    private final Collection<Execution> executions = new LinkedList<>();


    public synchronized void add(Order order) {
        orders.add(order);
    }

    public synchronized boolean isOpen() {
        return open;
    }

    public boolean isClosed() {
        return !isOpen();
    }

    public synchronized void open() {
        if (isClosed()) {
            if (executions.isEmpty()) { // can re-open
                open = true;
            } else {
                // log message that an order book with executions cannot be re-opened
            }
        }
    }

    public synchronized void close() {
        if (isOpen()) {
            open = false;
        }
    }

    public synchronized void addExecution(Execution execution) {
        if (!executions.isEmpty()) {
            Execution first = executions.iterator().next();
            if (first.getPrice() != execution.getPrice()) {
                throw new IllegalArgumentException("execution price must be the same for all executions of an order book");
            }
        }
        executions.add(execution);
    }

    public int getOrderCount() {
        return orders.size();
    }

    public double getDemand() {
        return orders.stream().filter(this::isOrderValid).mapToDouble(Order::getQuantity).sum();
    }

    private boolean isOrderValid(Order order) {
        if (executions.isEmpty()) {
            return true;
        }

        if (order.getType() != Order.Type.LIMIT) {
            return true;
        }

        double execPrice = executions.iterator().next().getPrice();
        double limitPrice = order.getPrice();

        return limitPrice < execPrice;
    }
}
