package store.sale.domain;

import store.domain.Product;

import java.math.BigInteger;

public class Order {

    private final Product product;
    private final BigInteger number;

    public Order(Product product, BigInteger number) {
        this.product = product;
        this.number = number;
    }
}
