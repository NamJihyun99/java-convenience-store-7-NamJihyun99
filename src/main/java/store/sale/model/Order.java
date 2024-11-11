package store.sale.model;


import store.domain.Product;

import java.math.BigInteger;

public record Order(Product product, BigInteger amount) {
}
