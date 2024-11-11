package store.sale.service;

import store.domain.Inventory;
import store.domain.Product;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.sale.common.DateTime;
import store.sale.domain.Order;
import store.sale.model.PurchasingPlan;
import store.sale.view.ProductAmountDto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static store.sale.common.InputValidationExceptionCode.QUANTITY_EXCEED;
import static store.sale.common.SaleExceptionCode.PRODUCT_NOT_FOUND;

public class SaleService {

    private final ProductRepository productRepository;

    public SaleService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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

    public void validateOrderProducts(List<String> order) {
        Product product = productRepository.findByName(order.getFirst())
                .orElseThrow(() -> new IllegalArgumentException(PRODUCT_NOT_FOUND.message));
        BigInteger quantity = product.quantity();
        if (product.getPromotionInventory().isPresent()) {
            quantity = quantity.add(product.getPromotionInventory().get().quantity());
        }
        BigInteger demandAmount = new BigInteger(order.getLast());
        if (quantity.compareTo(demandAmount) < 0) {
            throw new IllegalArgumentException(QUANTITY_EXCEED.message);
        }
    }

    public void deleteQuantity(PurchasingPlan plan) {
        plan.getForms().values().forEach(form -> {
            Product product = productRepository.findByName(form.getProduct().name())
                    .orElseThrow(() -> new IllegalStateException(PRODUCT_NOT_FOUND.message));
            if (form.getProduct().getPromotionInventory().isPresent()) {
                product.subtractPromotionQuantity(form.getPromotionBuyAmount().add(form.getUnpayedAmount()));
            }
            product.subtractQuantity(form.getNonPromotionAmount());
        });
    }

    public List<ProductAmountDto> getEnableProduct(List<Order> orders, DateTime dateTime) {
        List<ProductAmountDto> result = new ArrayList<>();
        orders.forEach(order -> {
            Product product = order.product();
            if (extraGetExisted(dateTime, order, product.getPromotionInventory(), product)) {
                result.add(new ProductAmountDto(product.name(), product.getExtraGet(order.amount())));
            }
        });
        return result;
    }

    private static boolean extraGetExisted(DateTime dateTime, Order order, Optional<Inventory> promotionInventory, Product product) {
        return promotionInventory.isPresent()
                && promotionInventory.get().promotion().enable(dateTime.now())
                && product.getExtraGet(order.amount()).compareTo(BigInteger.ZERO) > 0;
    }

}
