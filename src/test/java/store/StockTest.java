package store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StockTest {

    @DisplayName("구매한 상품의 개수를 받아 상품의 재고가 충분할 경우 재고를 차감한다.")
    @Test
    void 재고_차감_성공() {
        Stock stock = new Stock("콜라",1000,10);
        stock.deduct(2);
        assertThat(stock.quantity()).isEqualTo(8);
    }
}
