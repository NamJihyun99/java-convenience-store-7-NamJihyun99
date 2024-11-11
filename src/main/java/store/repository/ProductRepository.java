package store.repository;

import store.domain.Product;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);
    Optional<Product> findByName(String name);
    List<Product> findAll();
    BigInteger count();
}
