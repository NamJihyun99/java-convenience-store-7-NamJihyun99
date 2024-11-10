package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.inventory.common.ExceptionCode.QUANTITY_SHORTAGE;

/**
 * 가격과 프로모션, 재고를 하나로 묶는 개념
 * 재고를 감하는 로직이 여기로 옮겨와야...
 * */
class InventoryTest {

    @DisplayName("구매한 상품의 개수를 받아 상품의 재고가 충분할 경우 재고를 차감한다.")
    @Test
    void 재고_차감_성공() {
        Inventory inventory = new Inventory(1000L, BigInteger.TEN, new NonePromotion());
        inventory.deduct(BigInteger.TWO);
        assertThat(inventory.quantity()).isEqualTo(BigInteger.valueOf(8));
    }

    @DisplayName("구매한 상품의 개수를 받아 상품의 재고가 부족할 경우 예외가 발생한다.")
    @Test
    void 재고_부족() {
        Inventory inventory = new Inventory(1000L, BigInteger.TEN, new NonePromotion());
        assertThatThrownBy(() -> inventory.deduct(BigInteger.valueOf(20)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(QUANTITY_SHORTAGE.message);
    }
}