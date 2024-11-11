package store.sale.model;

import store.domain.Inventory;
import store.domain.Product;
import store.domain.Promotion;
import store.sale.common.DateTime;
import store.sale.domain.Order;
import store.sale.view.ProductAmountDto;

import java.math.BigInteger;
import java.util.*;

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
                form.addPromotionAmount(order.product().getPromotionQuantity(order.amount(), dateTime));
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

    public void addNonPromotion(ProductAmountDto dto) {
        Form form = forms.get(dto.name());
        form.subtractPromotionAmount(dto.amount());
    }

    public void subtractNonPromotions(String productName) {
        Form form = forms.get(productName);
        form.nonPromotionAmount = BigInteger.ZERO;
    }

    public List<ProductAmountDto> promotionQuantityShortages() {
        List<ProductAmountDto> dtos = new ArrayList<>();
        forms.values().stream()
                .filter(form -> form.product.getPromotionInventory().isPresent())
                .forEach(form ->
                        dtos.add(new ProductAmountDto(form.product.name(), form.nonPromotionAmount))
                );
        return dtos;
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

        public BigInteger getPayedAmount() {
            return promotionBuyAmount.add(nonPromotionAmount);
        }

        public BigInteger getUnpayedAmount() {
            return promotionGetAmount;
        }

        void addPromotionAmount(BigInteger totalAmount) {
            Promotion promotion = validPromotion(totalAmount);
            BigInteger divide = totalAmount.divide(promotion.buy().add(promotion.get()));
            this.promotionBuyAmount = this.promotionBuyAmount.add(promotion.buy().multiply(divide));
            this.promotionGetAmount = this.promotionGetAmount.add(promotion.get().multiply(divide));
            this.nonPromotionAmount = this.nonPromotionAmount.subtract(totalAmount);
        }

        void subtractPromotionAmount(BigInteger totalAmount) {
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

        BigInteger getNonPromotionPrice() {
            return nonPromotionAmount.multiply(BigInteger.valueOf(product.price()));
        }
    }
}