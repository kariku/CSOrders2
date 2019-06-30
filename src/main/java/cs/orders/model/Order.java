package cs.orders.model;

import java.util.Date;
import java.util.Objects;

public class Order {
    public enum Type {
        LIMIT,
        MARKET
    }

    private final String instrumentId;
    private final Type type;
    private final double quantity;
    private final Date entryDate;
    private final double price;
    private final String orderId;       // generated, unique

    // in a real system this should be injected
    private static final OrderIdGenerator idGenerator= new OrderIdGenerator();

    public Order(String instrumentId, Type type, double quantity, Date entryDate, double price) {
        this.instrumentId = Objects.requireNonNull(instrumentId);
        this.type = Objects.requireNonNull(type);
        if (quantity <= 0.0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.quantity = quantity;
        this.entryDate = Objects.requireNonNull(entryDate);
        if (type == Type.LIMIT) {
            if (price <= 0.0) {
                throw new IllegalArgumentException("Price must be positive");
            }
            this.price = price;
        } else {
            this.price = 0.0;
        }
        this.orderId = idGenerator.getNextId();
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public Type getType() {
        return type;
    }

    public boolean isLimitOrder() {
        return type == Type.LIMIT;
    }

    public double getQuantity() {
        return quantity;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public double getPrice() {
        return price;
    }

    public String getOrderId() {
        return orderId;
    }



    public static class Builder {
        private String instrumentId;
        private Type type = Type.LIMIT;
        private double quantity;
        private Date entryDate;
        private double price;

        public Order build() {
            return new Order(instrumentId, type, quantity, entryDate, price);
        }

        public Builder withInstrumentId(String instrumentId) {
            this.instrumentId = instrumentId;
            return this;
        }

        public Builder withType(Type type) {
            this.type = type;
            return this;
        }

        public Builder withQuantity(double quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder withEntryDate(Date entryDate) {
            this.entryDate = entryDate;
            return this;
        }

        public Builder withPrice(double price) {
            this.price = price;
            return this;
        }
    }
}
