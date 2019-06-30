package cs.orders;

public class Execution {
    private final double quantity;
    private final double price;

    public Execution(double quantity, double price) {
        this.quantity = quantity;
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
