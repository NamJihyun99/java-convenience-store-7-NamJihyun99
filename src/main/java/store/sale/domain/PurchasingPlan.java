package store.sale.domain;

import store.domain.Product;
import store.sale.view.ProductAmountDto;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchasingPlan {

    final Map<String, BngoProducts> bngoProducts = new HashMap<>();
    final Map<String, NonePromotionProduct> nonePromotionProducts = new HashMap<>();

    public PurchasingPlan(List<Order> orders) {
        orders.forEach(order ->
                nonePromotionProducts.put(order.product().name(), new NonePromotionProduct(order.product(), order.amount()))
        );
    }

    public void addFreeGet(ProductAmountDto dto) {
        bngoProducts.get(dto.name()).plusGet(dto.amount());
        nonePromotionProducts.get(dto.name()).subtractAmount(dto.amount());
    }

    public BigInteger getTotal() {
        BigInteger sum = BigInteger.ZERO;
        for (BngoProducts bngoProducts : bngoProducts.values()) {
            sum = sum.add(bngoProducts.getTotal());
        }
        for (NonePromotionProduct nonePromotionProduct : nonePromotionProducts.values()) {
            sum = sum.add(nonePromotionProduct.getTotal());
        }
        return sum;
    }

    private static class BngoProducts {
        Product product;
        BigInteger buyAmount;
        BigInteger getAmount;
        BigInteger price;

        BngoProducts(Product product) {
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
