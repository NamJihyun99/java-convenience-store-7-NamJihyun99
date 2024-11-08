package store.repository;

import store.domain.Product;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryProductRepository implements ProductRepository {

    private static final Map<String, Product> products = new ConcurrentHashMap<>();

    @Override
    public Product save(Product product) {
        products.put(product.name(), product);
        return product;
    }

    @Override
    public Product findByName(String name) {
        return products.get(name);
    }
}
