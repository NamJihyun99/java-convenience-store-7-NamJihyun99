package store.domain;

import store.sale.common.DateTime;

import java.math.BigInteger;
import java.util.function.BiPredicate;

import static store.inventory.common.ExceptionCode.QUANTITY_SHORTAGE;

public class Inventory {

    private final Promotion promotion;
    private BigInteger quantity;

    public Inventory(BigInteger quantity, Promotion promotion) {
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public void deduct(BigInteger number) {
        if (quantity.compareTo(number) < 0) {
            throw new IllegalStateException(QUANTITY_SHORTAGE.message);
        }
        quantity = quantity.subtract(number);
    }

    public BigInteger getPromotionQuantity(BigInteger demand, DateTime dateTime) {
        if (!promotion.enable(dateTime.now())) {
            return BigInteger.ZERO;
        }
        return countTurns(demand, this::sumLessThanDemand).multiply(promotion.buy().add(promotion.get()));
    }

    public BigInteger enableExtraGet(BigInteger demand) {
        BigInteger count = countTurns(demand, this::buyLessThanDemand);
        BigInteger extraGet = count.multiply(promotion.buy().add(promotion.get())).subtract(demand);
        if (extraGet.compareTo(BigInteger.ZERO) <= 0) {
            extraGet = BigInteger.ZERO;
        }
        return extraGet;
    }

    private BigInteger countTurns(BigInteger demand, BiPredicate<BigInteger, BigInteger> totalCondition) {
        BigInteger count = BigInteger.ZERO;
        BigInteger total = BigInteger.ZERO;
        while (totalCondition.test(demand, total) && lessThanQuantity(total)) {
            count = count.add(BigInteger.ONE);
            total = total.add(promotion.buy().add(promotion.get()));
        }
        return count;
    }

    private boolean lessThanQuantity(BigInteger total) {
        return total.add(promotion.buy().add(promotion.get())).compareTo(quantity) <= 0;
    }

    private boolean buyLessThanDemand(BigInteger demand, BigInteger total) {
        return total.add(promotion.buy()).compareTo(demand) <= 0;
    }

    private boolean sumLessThanDemand(BigInteger demand, BigInteger total) {
        return total.add(promotion.buy().add(promotion.get())).compareTo(demand) <= 0;
    }

    public BigInteger quantity() {
        return quantity;
    }

    public Promotion promotion() {
        return promotion;
    }
}
