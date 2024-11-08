package store.repository;

import store.domain.Product;

public interface ProductRepository {

    Product save(Product product);
    Product findByName(String name);
}
