package store.sale.domain;

import store.domain.Inventory;
import store.domain.Product;
import store.domain.Promotion;
import store.sale.common.DateTime;
import store.sale.view.ProductAmountDto;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static store.sale.common.SaleExceptionCode.PROMOTION_NOT_EXISTED;
import static store.sale.common.SaleExceptionCode.PROMOTION_UNABLE;

public class PurchasingPlan {

    private final DateTime dateTime;
    private final Map<String, Form> forms = new HashMap<>();

    public PurchasingPlan(DateTime dateTime, List<Order> orders) {
        this.dateTime = dateTime;
        orders.forEach(order -> {
            Form form = new Form(order.product(), order.amount());
            if (order.product().getPromotionInventory().isPresent()) {
                form.addPromotionAmount(order.product().getPromotionQuantity(order.amount(), dateTime));
            }
            forms.put(order.product().name(), form);
        });
    }

    public void addFreeGet(ProductAmountDto dto) {
        Form form = forms.get(dto.name());
        form.addExtraNonPromotionAmount(dto.amount());
        form.addPromotionAmount(form.product.getPromotionQuantity(form.nonPromotionAmount, dateTime));
    }

    public BigInteger getTotal() {
        BigInteger sum = BigInteger.ZERO;
        for (Form form : forms.values()) {
            sum = sum.add(form.getTotalPrice());
        }
        return sum;
    }

    public BigInteger getGetTotal() {
        BigInteger sum = BigInteger.ZERO;
        for (Form form : forms.values()) {
            sum = sum.add(form.getGetPrice());
        }
        return sum;
    }

    private static class Form {
        Product product;
        BigInteger promotionBuyAmount;
        BigInteger promotionGetAmount;
        BigInteger nonPromotionAmount;

        public Form(Product product, BigInteger nonPromotionAmount) {
            this.product = product;
            this.promotionBuyAmount = BigInteger.ZERO;
            this.promotionGetAmount = BigInteger.ZERO;
            this.nonPromotionAmount = nonPromotionAmount;
        }

        void addPromotionAmount(BigInteger totalAmount) {
            Promotion promotion = validPromotion(totalAmount);
            BigInteger divide = totalAmount.divide(promotion.buy().add(promotion.get()));
            this.promotionBuyAmount = this.promotionBuyAmount.add(promotion.buy().multiply(divide));
            this.promotionGetAmount = this.promotionGetAmount.add(promotion.get().multiply(divide));
            this.nonPromotionAmount = this.nonPromotionAmount.subtract(totalAmount);
        }

        void subtractPromotionAmount(BigInteger totalAmount, DateTime dateTime) {
            Promotion promotion = validPromotion(totalAmount);
            BigInteger divide = totalAmount.divide(promotion.buy().add(promotion.get()));
            this.promotionBuyAmount = this.promotionBuyAmount.subtract(promotion.buy().multiply(divide));
            this.promotionGetAmount = this.promotionGetAmount.subtract(promotion.get().multiply(divide));
            this.nonPromotionAmount = this.nonPromotionAmount.add(totalAmount);
        }

        void addExtraNonPromotionAmount(BigInteger amount) {
            this.nonPromotionAmount = this.nonPromotionAmount.add(amount);
        }

        private Promotion validPromotion(BigInteger totalAmount) {
            Inventory inventory = product.getPromotionInventory()
                    .orElseThrow(() -> new IllegalStateException(PROMOTION_NOT_EXISTED.message));
            Promotion promotion = inventory.promotion();
            if (totalAmount.mod(promotion.buy().add(promotion.get())).compareTo(BigInteger.ZERO) != 0) {
                throw new IllegalArgumentException(PROMOTION_UNABLE.message);
            }
            return promotion;
        }

        BigInteger getTotalPrice() {
            BigInteger sum = BigInteger.ZERO;
            if (product.getPromotionInventory().isPresent()) {
                sum = sum.add(promotionBuyAmount).add(promotionGetAmount).multiply(BigInteger.valueOf(product.price()));
            }
            return sum.add(nonPromotionAmount.multiply(BigInteger.valueOf(product.price())));
        }

        BigInteger getGetPrice() {
            BigInteger sum = BigInteger.ZERO;
            if (product.getPromotionInventory().isPresent()) {
                sum = sum.add(promotionGetAmount).multiply(BigInteger.valueOf(product.price()));
            }
            return sum;
        }
    }
}
