package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.common.ExceptionCode.QUANTITY_SHORTAGE;

public class StockTest {

    @DisplayName("구매한 상품의 개수를 받아 상품의 재고가 충분할 경우 재고를 차감한다.")
    @Test
    void 재고_차감_성공() {
        Stock stock = new Stock("콜라",1000L,BigInteger.TEN);
        stock.deduct(BigInteger.TWO);
        assertThat(stock.quantity()).isEqualTo(BigInteger.valueOf(8));
    }

    @DisplayName("구매한 상품의 개수를 받아 상품의 재고가 부족할 경우 예외가 발생한다.")
    @Test
    void 재고_부족() {
        Stock stock = new Stock("콜라",1000L,BigInteger.TEN);
        assertThatThrownBy(() -> stock.deduct(BigInteger.valueOf(20)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(QUANTITY_SHORTAGE.message);
    }
}
