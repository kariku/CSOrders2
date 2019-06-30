package cs.orders.model;

public class Execution {
    private final double quantity;
    private final double price;

    public Execution(double quantity, double price) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be positive");
        }
        this.quantity = quantity;
        if (price <= 0) {
            throw new IllegalArgumentException("price must be positive");
        }
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
