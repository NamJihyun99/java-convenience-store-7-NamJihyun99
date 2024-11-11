package store.sale.model;

import store.domain.Inventory;
import store.domain.Product;
import store.domain.Promotion;
import store.sale.common.DateTime;
import store.sale.domain.Order;
import store.sale.dto.ProductAmountDto;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static store.sale.common.SaleExceptionCode.PROMOTION_NOT_EXISTED;
import static store.sale.common.SaleExceptionCode.PROMOTION_UNABLE;

public class PurchasingPlan {

    private static final BigInteger DISCOUNT_PERCENTAGE = BigInteger.valueOf(30);
    private static final BigInteger DISCOUNT_MAX = BigInteger.valueOf(8000);

    private final DateTime dateTime;
    private final Map<String, Form> forms = new HashMap<>();
    private boolean discountable = false;

    public PurchasingPlan(DateTime dateTime, List<Order> orders) {
        this.dateTime = dateTime;
        orders.forEach(order -> {
            Form form = new Form(order.product(), order.amount());
            if (order.product().getPromotionInventory().isPresent()) {
                BigInteger tmp = order.product().getPromotionQuantity(order.amount(), dateTime);
                form.addPromotionAmount(tmp);
            }
            forms.put(order.product().name(), form);
        });
    }

    public Map<String, Form> getForms() {
        return Collections.unmodifiableMap(forms);
    }

    public void applyMembership() {
        discountable = true;
    }

    public void addFreeGet(ProductAmountDto dto) {
        Form form = forms.get(dto.name());
        form.addExtraNonPromotionAmount(dto.amount());
        form.addPromotionAmount(form.product.getPromotionQuantity(form.nonPromotionAmount, dateTime));
    }

    public void subtractNonPromotions(String productName) {
        Form form = forms.get(productName);
        form.nonPromotionAmount = BigInteger.ZERO;
    }

    public List<ProductAmountDto> promotionQuantityShortages() {
        return forms.values().stream()
                .filter(form -> form.product.getPromotionInventory().isPresent())
                .filter(form -> form.product.getPromotionInventory().get().promotion().enable(dateTime.now()))
                .filter(form -> form.nonPromotionAmount.compareTo(BigInteger.ZERO) > 0)
                .map(form -> new ProductAmountDto(form.product.name(), form.nonPromotionAmount))
                .collect(Collectors.toList());
    }

    public BigInteger getTotal() {
        BigInteger sum = BigInteger.ZERO;
        for (Form form : forms.values()) {
            sum = sum.add(form.getTotalPrice());
        }
        return sum;
    }

    public BigInteger getPromotionDiscount() {
        BigInteger sum = BigInteger.ZERO;
        for (Form form : forms.values()) {
            sum = sum.add(form.getGetPrice());
        }
        return sum;
    }

    public BigInteger getMembershipDiscount() {
        if (discountable) {
            BigInteger discount = getNonPromotionTotal().multiply(DISCOUNT_PERCENTAGE).divide(BigInteger.valueOf(100));
            if (discount.compareTo(DISCOUNT_MAX) > 0) {
                return DISCOUNT_MAX;
            }
            return discount;
        }
        return BigInteger.ZERO;
    }

    private BigInteger getNonPromotionTotal() {
        BigInteger sum = BigInteger.ZERO;
        for (Form form : forms.values()) {
            sum = sum.add(form.getNonPromotionPrice());
        }
        return sum;
    }

    public static class Form {
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

        public Product getProduct() {
            return product;
        }

        public BigInteger getPromotionBuyAmount() {
            return promotionBuyAmount;
        }

        public BigInteger getNonPromotionAmount() {
            return nonPromotionAmount;
        }

        public BigInteger getUnpayedAmount() {
            return promotionGetAmount;
        }

        private void addPromotionAmount(BigInteger totalAmount) {
            Promotion promotion = validPromotion(totalAmount);
            BigInteger divide = totalAmount.divide(promotion.buy().add(promotion.get()));
            this.promotionBuyAmount = this.promotionBuyAmount.add(promotion.buy().multiply(divide));
            this.promotionGetAmount = this.promotionGetAmount.add(promotion.get().multiply(divide));
            this.nonPromotionAmount = this.nonPromotionAmount.subtract(totalAmount);
        }

        private void addExtraNonPromotionAmount(BigInteger amount) {
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

        private BigInteger getTotalPrice() {
            BigInteger sum = BigInteger.ZERO;
            if (product.getPromotionInventory().isPresent()) {
                sum = sum.add(promotionBuyAmount).add(promotionGetAmount).multiply(BigInteger.valueOf(product.price()));
            }
            return sum.add(nonPromotionAmount.multiply(BigInteger.valueOf(product.price())));
        }

        private BigInteger getGetPrice() {
            BigInteger sum = BigInteger.ZERO;
            if (product.getPromotionInventory().isPresent()) {
                sum = sum.add(promotionGetAmount).multiply(BigInteger.valueOf(product.price()));
            }
            return sum;
        }

        private BigInteger getNonPromotionPrice() {
            return nonPromotionAmount.multiply(BigInteger.valueOf(product.price()));
        }
    }
}
