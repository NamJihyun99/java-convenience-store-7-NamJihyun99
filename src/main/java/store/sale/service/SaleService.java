package store.sale.service;

import store.domain.Product;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.sale.domain.Order;
import store.sale.view.ProductAmountDto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static store.sale.common.SaleExceptionCode.PRODUCT_NOT_FOUND;

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
            Product product = productRepository.findByName(requestToken.getFirst())
                    .orElseThrow(() -> new IllegalArgumentException(PRODUCT_NOT_FOUND.message));
            orders.add(new Order(product, new BigInteger(requestToken.getLast())));
        });
        return orders;
    }

    public List<ProductAmountDto> getEnableProduct(List<Order> orders) {
        return orders.stream().map(Order::getExtraGet)
                .filter(extraGet -> extraGet.size() == 1)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}
