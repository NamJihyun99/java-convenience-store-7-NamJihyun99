package store.domain;

import store.inventory.common.ExceptionCode;
import store.sale.common.DateTime;

import java.math.BigInteger;
import java.util.Objects;
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

    public BigInteger getPromotionQuantity(BigInteger demand, DateTime dateTime) {
        return promotionInventory.getPromotionQuantity(demand, dateTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
