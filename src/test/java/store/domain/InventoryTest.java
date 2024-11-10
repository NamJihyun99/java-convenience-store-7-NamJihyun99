package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.inventory.common.ExceptionCode.QUANTITY_SHORTAGE;

class InventoryTest {

    @DisplayName("구매한 상품의 개수를 받아 상품의 재고가 충분할 경우 재고를 차감한다.")
    @Test
    void 재고_차감_성공() {
        Promotion promotion = createPromotion();
        Inventory inventory = new Inventory(1000L, BigInteger.TEN, promotion);
        inventory.deduct(BigInteger.TWO);
        assertThat(inventory.quantity()).isEqualTo(BigInteger.valueOf(8));
    }

    @DisplayName("구매한 상품의 개수를 받아 상품의 재고가 부족할 경우 예외가 발생한다.")
    @Test
    void 재고_부족() {
        Promotion promotion = createPromotion();
        Inventory inventory = new Inventory(1000L, BigInteger.TEN, promotion);
        assertThatThrownBy(() -> inventory.deduct(BigInteger.valueOf(20)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(QUANTITY_SHORTAGE.message);
    }

    @ParameterizedTest
    @DisplayName("사용자가 필요한 상품의 개수를 받아 추가로 증정 가능한 개수를 계산한다.")
    @CsvSource(value = {"1,0", "2,1", "5,1", "10,0"})
    void 증정_계산(String demand, String answer) {
        Promotion promotion = createPromotion();
        Inventory inventory = new Inventory(1000L, BigInteger.valueOf(7L), promotion);
        assertThat(inventory.enableExtraGet(new BigInteger(demand))).isEqualTo(new BigInteger(answer));
    }

    private static Promotion createPromotion() {
        return Promotion.create("탄산2+1",
                BigInteger.TWO, BigInteger.ONE,
                LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"));
    }
}