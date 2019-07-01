package cs.orders.model;

import java.util.HashMap;
import java.util.Map;

public class OrderManager {
    private static final Map<String, OrderBook> orderBooksByInstrumentId = new HashMap<>();
    private static final Map<String, Order> ordersById = new HashMap<>();


    public OrderBook getOrderBook(String instrumentId) {
        OrderBook orderBook = orderBooksByInstrumentId.get(instrumentId);
        if (orderBook == null) {
            throwNotFound(instrumentId);
        }
        return orderBook;
    }

    public Order getOrder(String orderId) {
        return ordersById.get(orderId);
    }

    public OrderBook openOrderBook(String instrumentId) {
        OrderBook orderBook = orderBooksByInstrumentId.merge(instrumentId, new OrderBook(instrumentId), (oldVal, newVal) -> newVal);
        orderBook.open();
        return orderBook;
    }

    public OrderBook closeOrderBook(String instrumentId) {
        OrderBook orderBook = getOrderBook(instrumentId);
        if (orderBook == null) {
            throwNotFound(instrumentId);
        }
        orderBook.close();
        return orderBook;
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

    private static void throwNotFound(String instrumentId) {
        throw new IllegalArgumentException(String.format("order book for instrument %s not found", instrumentId));
    }
}
