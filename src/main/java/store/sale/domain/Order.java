package store.sale.domain;


import store.domain.Product;

import java.math.BigInteger;

public record Order(Product product, BigInteger amount) {
}
