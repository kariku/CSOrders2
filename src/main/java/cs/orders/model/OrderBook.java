package cs.orders.model;

import java.util.*;

public class OrderBook {
    private final String instrumentId;
    private final Collection<Order> orders = new LinkedList<>();
    private volatile boolean open = true;   // decided to use basic synchronization, to keep things simple
    private final Collection<Execution> executions = new LinkedList<>();

    // statistics
    private Order lastOrder;
    private Order biggestOrder;
    private Order smallestOrder;
    private Order earliestOrder;
    private final Map<Double, Double> limitBreakdown = new HashMap<>();

    public OrderBook(String instrumentId) {
        this.instrumentId = Objects.requireNonNull(instrumentId);
    }

    public synchronized void addOrder(Order order) {
        if (!instrumentId.equals(order.getInstrumentId())) {
            throw new IllegalArgumentException("wrong order for this book, bad instrument id");
        }

        if (isClosed()) {
            throw new IllegalStateException("cannot add orders to closed book");
        }

        orders.add(order);
        updateStatistics(order);
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
        if (isOpen()) {
            throw new IllegalStateException("cannot add executions to open book");
        }

        if (!executions.isEmpty()) {
            if (getExecutionPrice() != execution.getPrice()) {
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

    public Order getBiggestOrder() {
        return biggestOrder;
    }

    public Order getSmallestOrder() {
        return smallestOrder;
    }

    public boolean isOrderValid(Order order) {
        if (executions.isEmpty()) {
            return true;
        }

        if (!order.isLimitOrder()) {
            return true;
        }

        double execPrice = getExecutionPrice();
        double limitPrice = order.getPrice();

        return limitPrice < execPrice;
    }

    public double getExecutionQuantity() {
        return executions.stream().mapToDouble(Execution::getQuantity).sum();
    }

    public double getExecutionPrice() {
        if (executions.isEmpty()) {
            throw new IllegalStateException("bad call, there are no executions for this book");
        }
        return executions.iterator().next().getPrice();
    }

    private void updateStatistics(Order order) {
        lastOrder = order;

        if (biggestOrder == null || order.getQuantity() > biggestOrder.getQuantity()) {
            biggestOrder = order;
        }

        if (smallestOrder == null || order.getQuantity() < smallestOrder.getQuantity()) {
            smallestOrder = order;
        }

        if (earliestOrder == null || order.getEntryDate().compareTo(earliestOrder.getEntryDate()) < 0) {
            earliestOrder = order;
        }

        updateLimitBreakdown(order);
    }

    private void updateLimitBreakdown(Order order) {
        if (order.isLimitOrder()) {
            limitBreakdown.merge(order.getPrice(), order.getQuantity(), (oldQuantity, newQuantity) -> oldQuantity + newQuantity);
        }
    }

}
