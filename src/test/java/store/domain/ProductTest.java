package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.inventory.common.ExceptionCode.PROMOTION_EXCEED;

public class ProductTest {

    @DisplayName("새로운 프로모션 추가 성공")
    @Test
    void addPromotion_Success() {
        Product product = new Product("콜라");
        product.addInventory(new Inventory(1000L, BigInteger.TEN, NonePromotion.getInstance()));
        assertThat(product.inventories().size()).isEqualTo(1);
    }

    @DisplayName("적용된 프로모션이 2개 이상이면 예외를 발생한다")
    @Test
    void addPromotion_Exceed() {
        Product product = new Product("콜라");
        product.addInventory(new Inventory(1000L, BigInteger.TEN, NonePromotion.getInstance()));
        product.addInventory(new Inventory(1000L, BigInteger.TEN, NonePromotion.getInstance()));
        assertThatThrownBy(() -> product.addInventory(new Inventory(1000L, BigInteger.TEN, NonePromotion.getInstance())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(PROMOTION_EXCEED.message);
    }

}
