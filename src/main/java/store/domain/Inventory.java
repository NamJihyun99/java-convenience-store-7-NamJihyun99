package store.domain;

import java.math.BigInteger;

import static store.common.ExceptionCode.QUANTITY_SHORTAGE;

public class Inventory {

    private Long price;
    private BigInteger quantity;
    private Promotion promotion;

    public Inventory(Long price, BigInteger quantity, Promotion promotion) {
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public void deduct(BigInteger number) {
        if (quantity.compareTo(number) < 0) {
            throw new IllegalStateException(QUANTITY_SHORTAGE.message);
        }
        quantity = quantity.subtract(number);
    }

    public Long price() {
        return price;
    }

    public BigInteger quantity() {
        return quantity;
    }

    public Promotion promotion() {
        return promotion;
    }
}
