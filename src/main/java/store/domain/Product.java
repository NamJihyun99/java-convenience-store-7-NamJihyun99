package store.domain;

import store.inventory.common.ExceptionCode;
import store.sale.common.DateTime;

import java.math.BigInteger;
import java.util.Optional;

import static store.sale.common.SaleExceptionCode.AMOUNT_TOO_BIG;

public class Product {

    private final String name;
    private final Long price;
    private BigInteger quantity;
    private Inventory promotionInventory;

    public Product(String name, Long price) {
        validateName(name);
        this.name = name;
        this.price = price;
        this.quantity = BigInteger.ZERO;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(ExceptionCode.INCORRECT_NAME.message);
        }
    }

    public String name() {
        return name;
    }

    public BigInteger quantity() {
        return quantity;
    }

    public Long price() {
        return price;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    public void setPromotionInventory(Inventory inventory) {
        this.promotionInventory = inventory;
    }

    public Optional<Inventory> getPromotionInventory() {
        return Optional.ofNullable(this.promotionInventory);
    }

    public BigInteger getExtraGet(BigInteger demand) {
        return promotionInventory.enableExtraGet(demand);
    }

    public BigInteger getPromotionQuantity(BigInteger demand, DateTime dateTime) {
        return promotionInventory.getPromotionQuantity(demand, dateTime);
    }

    public void subtractQuantity(BigInteger amount) {
        if (quantity.compareTo(amount) < 0) {
            throw new IllegalArgumentException(AMOUNT_TOO_BIG.message);
        }
        quantity = quantity.subtract(amount);
    }

    public void subtractPromotionQuantity(BigInteger amount) {
        if (this.promotionInventory != null) {
            this.promotionInventory.deduct(amount);
        }
    }
}
