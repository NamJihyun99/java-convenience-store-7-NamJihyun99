package store.repository;

import store.domain.Product;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryProductRepository implements ProductRepository {

    private static final Map<String, Product> products = new ConcurrentHashMap<>();

    private MemoryProductRepository() {}

    public static MemoryProductRepository getInstance() {
        return new MemoryProductRepository();
    }

    @Override
    public Product save(Product product) {
        products.put(product.name(), product);
        return product;
    }

    @Override
    public Optional<Product> findByName(String name) {
        return Optional.ofNullable(products.get(name));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public BigInteger count() {
        return BigInteger.valueOf(products.size());
    }

    void clear() {
        products.clear();
    }
}
