package cs.orders.model;

import java.util.HashMap;
import java.util.Map;

public class OrderManager {
    private static final Map<String, OrderBook> orderBooksByInstrumentId = new HashMap<>();
    private static final Map<String, Order> ordersById = new HashMap<>();


    public OrderBook getOrderBook(String instrumentId) {
        return orderBooksByInstrumentId.get(instrumentId);
    }

    public Order getOrder(String orderId) {
        return ordersById.get(orderId);
    }

    public boolean isOrderValid(String orderId) {
        Order order = getOrder(orderId);
        OrderBook orderBook = findOrderBook(order);
        return orderBook.isOrderValid(order);
    }

    public double getExecutionQuantity(String orderId) {
        Order order = getOrder(orderId);
        OrderBook orderBook = findOrderBook(order);
        return orderBook.getExecutionQuantity();
    }

    public double getExecutionPrice(String orderId) {
        Order order = getOrder(orderId);
        OrderBook orderBook = findOrderBook(order);
        return orderBook.getExecutionPrice();
    }

    private OrderBook findOrderBook(Order order) {
        String instrumentId = order.getInstrumentId();
        return getOrderBook(instrumentId);
    }
}
