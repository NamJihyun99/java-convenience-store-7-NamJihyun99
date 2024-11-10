package store.sale.domain;

import store.domain.Product;
import store.sale.view.ProductAmountDto;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchasingPlan {

    final Map<String, PromotionProduct> promotionProducts = new HashMap<>();
    final Map<String, NonePromotionProduct> nonePromotionProducts = new HashMap<>();

    public PurchasingPlan(List<Order> orders) {
        orders.forEach(order ->
                nonePromotionProducts.put(order.product().name(), new NonePromotionProduct(order.product(), order.amount()))
        );
    }

    public void addFreeGet(ProductAmountDto dto) {
        promotionProducts.get(dto.name()).plusGet(dto.amount());
        nonePromotionProducts.get(dto.name()).subtractAmount(dto.amount());
    }

    public BigInteger getTotal() {
        BigInteger sum = BigInteger.ZERO;
        for (PromotionProduct promotionProduct : promotionProducts.values()) {
            sum = sum.add(promotionProduct.getTotal());
        }
        for (NonePromotionProduct nonePromotionProduct : nonePromotionProducts.values()) {
            sum = sum.add(nonePromotionProduct.getTotal());
        }
        return sum;
    }

    private static class PromotionProduct {
        Product product;
        BigInteger buyAmount;
        BigInteger getAmount;
        BigInteger price;

        PromotionProduct(Product product) {
            this.product = product;
        }

        void plusGet(BigInteger amount) {
            this.getAmount = this.getAmount.add(amount);
        }

        BigInteger getTotal() {
            return buyAmount.multiply(this.price);
        }
    }

    private static class NonePromotionProduct {
        Product product;
        BigInteger buyAmount;

        NonePromotionProduct(Product product, BigInteger buyAmount) {
            this.product = product;
            this.buyAmount = buyAmount;
        }

        BigInteger getTotal() {
            return buyAmount.multiply(BigInteger.valueOf(this.product.price()));
        }

        void addAmount(BigInteger amount) {
            this.buyAmount = this.buyAmount.add(amount);
        }

        void subtractAmount(BigInteger amount) {
            this.buyAmount = this.buyAmount.subtract(amount);
        }
    }
}
