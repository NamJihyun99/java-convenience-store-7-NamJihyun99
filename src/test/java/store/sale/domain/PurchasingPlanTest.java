package store.sale.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Inventory;
import store.domain.Product;
import store.domain.Promotion;
import store.sale.common.DateTime;
import store.sale.common.FixedDateTime;
import store.sale.view.ProductAmountDto;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PurchasingPlanTest {

    @DisplayName("추가할 수량을 선택하면 고려하여 프로모션을 다시 적용한다.")
    @Test
    void 수량_추가_프로모션_적용() {
        Product product = new Product("콜라", 1000L);
        product.setQuantity(BigInteger.TEN);
        Promotion promotion = Promotion.create("탄산2+1",
                BigInteger.TWO, BigInteger.ONE,
                LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"));
        product.setPromotionInventory(new Inventory(BigInteger.valueOf(7), promotion));
        PurchasingPlan plan = new PurchasingPlan(
                testDateTime(),
                List.of(new Order(product, BigInteger.valueOf(2)))
        );
        assertThat(plan.getGetTotal()).isEqualTo(BigInteger.ZERO);
        plan.addFreeGet(new ProductAmountDto("콜라", BigInteger.ONE));
        assertThat(plan.getGetTotal()).isEqualTo(BigInteger.valueOf(1000));
    }

    @DisplayName("프로모션 적용 가능하면 재고를 고려하여 최대한 프로모션을 우선적으로 적용하여 지불 금액을 계산한다.")
    @Test
    void 프로모션_적용_금액() {
        Product product = new Product("콜라", 1000L);
        product.setQuantity(BigInteger.TEN);
        Promotion promotion = Promotion.create("탄산2+1",
                BigInteger.TWO, BigInteger.ONE,
                LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"));
        product.setPromotionInventory(new Inventory(BigInteger.valueOf(7), promotion));
        PurchasingPlan plan = new PurchasingPlan(
                testDateTime(),
                List.of(new Order(product, BigInteger.valueOf(10)))
        );
        assertThat(plan.getGetTotal()).isEqualTo(2000L);
    }

    @DisplayName("정가로 지불할 금액을 계산한다.")
    @Test
    void 정가_계산_금액() {
        Product product = new Product("콜라", 1000L);
        product.setQuantity(BigInteger.TEN);
        PurchasingPlan plan = new PurchasingPlan(
                testDateTime(),
                List.of(new Order(product, BigInteger.valueOf(10)))
        );
        assertThat(plan.getTotal()).isEqualTo(10000L);
    }

    @DisplayName("프로모션 적용 불가능하면 정가로 지불 금액을 계산한다.")
    @Test
    void 프로모션_적용_불가_금액() {
        Product product = new Product("콜라", 1000L);
        product.setQuantity(BigInteger.TEN);
        Promotion promotion = Promotion.create("탄산2+1",
                BigInteger.TWO, BigInteger.ONE,
                LocalDate.parse("2025-01-01"), LocalDate.parse("2025-12-31"));
        product.setPromotionInventory(new Inventory(BigInteger.valueOf(7), promotion));
        PurchasingPlan plan = new PurchasingPlan(
                testDateTime(),
                List.of(new Order(product, BigInteger.valueOf(3)))
        );
        assertThat(plan.getGetTotal()).isEqualTo(0L);
    }

    DateTime testDateTime() {
        return new FixedDateTime();
    }
}