package store;

public class Stock {

    private final String name;
    private final int price;
    private int quantity;

    public Stock(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int quantity() {
        return quantity;
    }

    public void deduct(int number) {
        if (quantity < number) {
            throw new IllegalStateException();
        }
        quantity -= number;
    }
}
