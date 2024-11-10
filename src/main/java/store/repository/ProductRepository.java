package store.repository;

import store.domain.Product;

import java.math.BigInteger;

public interface ProductRepository {

    Product save(Product product);
    Product findByName(String name);
    BigInteger count();
}
