package store.repository;

import store.domain.Product;

import java.math.BigInteger;
import java.util.List;

public interface ProductRepository {

    Product save(Product product);
    Product findByName(String name);
    List<Product> findAll();
    BigInteger count();
}
