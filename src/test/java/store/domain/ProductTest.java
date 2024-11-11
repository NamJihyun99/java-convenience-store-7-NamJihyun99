package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.sale.common.SaleExceptionCode.AMOUNT_TOO_BIG;

public class ProductTest {

    static final BigInteger PROMOTION = BigInteger.valueOf(6);
    static final BigInteger NON_PROMOTION = BigInteger.valueOf(7);

    @DisplayName("새로운 프로모션 추가 성공")
    @Test
    void addPromotion_Success() {
        Product product = createProductWithPromotion();
        assertThat(product.getPromotionInventory().get()).isInstanceOf(Inventory.class);
    }

    @DisplayName("정가 판매 재고 차감 성공")
    @Test
    void subtractQuantity() {
        Product product = createProductWithPromotion();
        product.subtractQuantity(BigInteger.ONE);
        assertThat(product.quantity()).isEqualTo(NON_PROMOTION.subtract(BigInteger.ONE));
    }

    @DisplayName("차감할 양보다 재고가 적으면 예외가 발생한다")
    @Test
    void subtractQuantityFail() {
        Product product = createProductWithPromotion();
        assertThatThrownBy(() -> product.subtractQuantity(BigInteger.TEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(AMOUNT_TOO_BIG.message);
    }

    @DisplayName("프로모션 판매 재고 차감 성공")
    @Test
    void subtractPromotionQuantity() {
        Product product = createProductWithPromotion();
        product.subtractPromotionQuantity(BigInteger.ONE);
        assertThat(product.getPromotionInventory().get().quantity()).isEqualTo(PROMOTION.subtract(BigInteger.ONE));
    }

    private static Product createProductWithPromotion() {
        Product product = new Product("콜라", 1000L);
        Promotion promotion = Promotion.create("탄산2+1",
                BigInteger.TWO, BigInteger.ONE,
                LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"));
        product.setPromotionInventory(new Inventory(PROMOTION, promotion));
        product.setQuantity(NON_PROMOTION);
        return product;
    }
}
