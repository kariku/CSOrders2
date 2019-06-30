package cs.orders.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static cs.orders.model.Constants.PRICE;
import static cs.orders.model.Constants.QUANTITY;
import static org.junit.jupiter.api.Assertions.*;

class ExecutionTest {


    @Test
    public void simpleTest() {
        Execution execution = new Execution(QUANTITY, PRICE);

        assertEquals(QUANTITY, execution.getQuantity());
        assertEquals(PRICE, execution.getPrice());
    }

    @Test
    public void badQuantityTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Execution(-QUANTITY, PRICE);
        });
    }

    @Test
    public void zeroQuantityTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Execution(0, PRICE);
        });
    }

    @Test
    public void badPriceTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Execution(QUANTITY, -PRICE);
        });
    }

    @Test
    public void zeroPriceTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Execution(QUANTITY, 0);
        });
    }
}