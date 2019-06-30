package cs.orders.model;

import org.junit.jupiter.api.Test;

import static cs.orders.model.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderBookTest {

    @Test
    public void emptyBookTest() {
        OrderBook orderBook = new OrderBook(INSTRUMENT_ID);

        assertTrue(orderBook.isOpen());
        assertFalse(orderBook.isClosed());
        assertEquals(0, orderBook.getOrderCount());
        assertEquals(0, orderBook.getDemand());
        assertEquals(0, orderBook.getExecutionQuantity());
    }

    @Test
    public void openOpenedBook() {
        OrderBook orderBook = new OrderBook(INSTRUMENT_ID);

        orderBook.open();

        assertTrue(orderBook.isOpen());
        assertFalse(orderBook.isClosed());
    }

    @Test
    public void closeOpenedBook() {
        OrderBook orderBook = new OrderBook(INSTRUMENT_ID);

        orderBook.close();

        assertFalse(orderBook.isOpen());
        assertTrue(orderBook.isClosed());
    }

    @Test
    public void reopenClosedBook() {
        OrderBook orderBook = new OrderBook(INSTRUMENT_ID);

        orderBook.close();
        orderBook.open();

        assertTrue(orderBook.isOpen());
        assertFalse(orderBook.isClosed());
    }

    @Test
    public void addOrderToOpenBook() {
        OrderBook orderBook = new OrderBook(INSTRUMENT_ID);
        Order order = makeOrder();

        orderBook.addOrder(order);

        assertTrue(orderBook.isOpen());
        assertFalse(orderBook.isClosed());
        assertEquals(1, orderBook.getOrderCount());
        assertEquals(QUANTITY, orderBook.getDemand());
    }

    @Test
    public void cannotAddOrdersToClosedBook() {
        OrderBook orderBook = new OrderBook(INSTRUMENT_ID);
        Order order = makeOrder();

        orderBook.close();

        assertThrows(IllegalStateException.class, () -> {
            orderBook.addOrder(order);
        });
    }

    @Test
    public void cannotAddExecutionsToOpenBook() {
        OrderBook orderBook = new OrderBook(INSTRUMENT_ID);
        Execution execution = makeExecution();

        assertThrows(IllegalStateException.class, () -> {
            orderBook.addExecution(execution);
        });
    }

    @Test
    public void addExecutionToClosedBook() {
        OrderBook orderBook = new OrderBook(INSTRUMENT_ID);
        orderBook.close();

        Execution execution = makeExecution();
        orderBook.addExecution(execution);

        assertEquals(QUANTITY, orderBook.getExecutionQuantity());
        assertEquals(PRICE, orderBook.getExecutionPrice());
    }

    @Test
    public void addMultipleExecutionsToClosedBook() {
        OrderBook orderBook = new OrderBook(INSTRUMENT_ID);
        orderBook.close();

        final double[] quantities = {123.45, 234.56, 345.67};
        double total = 0;
        for (double q: quantities) {
            Execution execution = new Execution(q, PRICE);
            orderBook.addExecution(execution);
            total += q;
        }

        assertEquals(total, orderBook.getExecutionQuantity(), DELTA);
        assertEquals(PRICE, orderBook.getExecutionPrice());
    }

    @Test
    public void cannotAddExecutionsWithDifferentPrices() {
        OrderBook orderBook = new OrderBook(INSTRUMENT_ID);
        orderBook.close();

        Execution execution1 = new Execution(111.11, 42.42);
        orderBook.addExecution(execution1);

        Execution execution2 = new Execution(111.11, 99.99);
        assertThrows(IllegalArgumentException.class, () -> {
            orderBook.addExecution(execution2);
        });
    }


    private Execution makeExecution() {
        return new Execution(QUANTITY, PRICE);
    }


    private Order makeOrder() {
        return new Order.Builder()
                .withInstrumentId(INSTRUMENT_ID)
                .withEntryDate(ENTRY_DATE)
                .withType(Order.Type.LIMIT)
                .withQuantity(QUANTITY)
                .withPrice(PRICE)
                .build();

    }

}