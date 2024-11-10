package store.sale.service;

import store.domain.Product;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.sale.domain.Order;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class SaleService {

    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    public SaleService(ProductRepository productRepository, PromotionRepository promotionRepository) {
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
    }

    public List<Product> readProducts() {
        return productRepository.findAll();
    }

    public List<Order> createOrders(List<List<String>> requestTokens) {
        List<Order> orders = new ArrayList<>();
        requestTokens.forEach(requestToken -> {
            Product product = productRepository.findByName(requestToken.getFirst()).orElseThrow();
            orders.add(new Order(product, new BigInteger(requestToken.getLast())));
        });
        return orders;
    }


}
