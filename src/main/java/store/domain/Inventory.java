package store.domain;

import java.math.BigInteger;

import static store.inventory.common.ExceptionCode.QUANTITY_SHORTAGE;

public class Inventory {

    private final Long price;
    private final Promotion promotion;
    private BigInteger quantity;

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
    
    public BigInteger enableExtraGet(BigInteger demand) {
        BigInteger count = countTurns(demand);
        BigInteger extraGet = count.multiply(promotion.buy().add(promotion.get())).subtract(demand);
        if (extraGet.compareTo(BigInteger.ZERO) <= 0) {
            extraGet = BigInteger.ZERO;
        }
        return extraGet;
    }

    private BigInteger countTurns(BigInteger demand) {
        BigInteger count = BigInteger.ZERO;
        BigInteger total = BigInteger.ZERO;
        while (total.add(promotion.buy()).compareTo(demand) <= 0 && total.add(promotion.buy().add(promotion.get())).compareTo(quantity) <= 0) {
            count = count.add(BigInteger.ONE);
            total = total.add(promotion.buy().add(promotion.get()));
        }
        return count;
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
