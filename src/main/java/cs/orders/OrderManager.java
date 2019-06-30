package cs.orders;

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
}
