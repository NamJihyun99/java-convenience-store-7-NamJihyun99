package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    @DisplayName("새로운 프로모션 추가 성공")
    @Test
    void addPromotion_Success() {
        Product product = new Product("콜라", 1000L);
        Promotion promotion = Promotion.create("탄산2+1",
                BigInteger.TWO, BigInteger.ONE,
                LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"));
        product.setPromotionInventory(new Inventory(1000L, BigInteger.TEN, promotion));
        assertThat(product.getPromotionInventory().get()).isInstanceOf(Inventory.class);
    }

}
