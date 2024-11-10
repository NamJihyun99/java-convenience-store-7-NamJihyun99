package store.domain;

import store.inventory.common.ExceptionCode;

import java.math.BigInteger;
import java.util.Optional;

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

}
