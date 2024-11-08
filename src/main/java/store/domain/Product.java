package store.domain;

import java.math.BigInteger;

import static store.common.ExceptionCode.QUANTITY_SHORTAGE;

public class Product {

    private final String name;
    private final Long price;
    private BigInteger quantity;

    public Product(String name, Long price, BigInteger quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public BigInteger quantity() {
        return quantity;
    }

    public void deduct(BigInteger number) {
        if (quantity.compareTo(number) < 0) {
            throw new IllegalStateException(QUANTITY_SHORTAGE.message);
        }
        quantity = quantity.subtract(number);
    }
}
