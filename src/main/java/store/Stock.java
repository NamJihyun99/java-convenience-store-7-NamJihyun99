package store;

import java.math.BigInteger;

public class Stock {

    private final String name;
    private final Long price;
    private BigInteger quantity;

    public Stock(String name, Long price, BigInteger quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public BigInteger quantity() {
        return quantity;
    }

    public void deduct(BigInteger number) {
        if (quantity.compareTo(number) < 0) {
            throw new IllegalStateException();
        }
        quantity = quantity.subtract(number);
    }
}
