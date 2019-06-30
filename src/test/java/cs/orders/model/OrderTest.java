package cs.orders.model;

import org.junit.jupiter.api.Test;

import static cs.orders.model.Constants.*;
import static org.junit.jupiter.api.Assertions.*;


class OrderTest {

    @Test
    public void simpleLimitOrderTest() {
        Order order = new Order(INSTRUMENT_ID, Order.Type.LIMIT, QUANTITY, ENTRY_DATE, PRICE);

        assertEquals(QUANTITY, order.getQuantity());
        assertEquals(Order.Type.LIMIT, order.getType());
        assertTrue(order.isLimitOrder());
        assertEquals(ENTRY_DATE, order.getEntryDate());
        assertEquals(PRICE, order.getPrice());
    }

    @Test
    public void simpleMarketOrderTest() {
        Order order = new Order(INSTRUMENT_ID, Order.Type.MARKET, QUANTITY, ENTRY_DATE, PRICE);

        assertEquals(QUANTITY, order.getQuantity());
        assertEquals(Order.Type.MARKET, order.getType());
        assertFalse(order.isLimitOrder());
        assertEquals(ENTRY_DATE, order.getEntryDate());
    }
}