package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.inventory.common.ExceptionCode;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BuyNGetOneFreeTest {

    @DisplayName("프로모션 객체 생성 성공")
    @Test
    void create_success() {
        Promotion promotion = Promotion.create("탄산2+1",
                BigInteger.TWO, BigInteger.ONE,
                LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"));
        assertThat(promotion).isInstanceOf(Promotion.class);

    }

    @DisplayName("프로모션 이름이 공백이면 예외가 발생한다.")
    @Test
    void create_name_blank() {
        assertThatThrownBy(() -> Promotion.create("",
                BigInteger.TWO, BigInteger.ONE,
                LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionCode.INCORRECT_NAME.message);
    }

    @DisplayName("프로모션 적용 상품 개수가 0보다 작거나 같으면 예외가 발생한다.")
    @Test
    void create_number_negative() {
        assertThatThrownBy(() -> Promotion.create("탄산2+1",
                BigInteger.TWO, BigInteger.ZERO,
                LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionCode.NUMBER_NEGATIVE.message);
    }

    @DisplayName("프로모션 종료날짜가 시작날짜보다 앞서면 예외가 발생한다.")
    @Test
    void create_period_not_matched() {
        assertThatThrownBy(() -> Promotion.create("탄산2+1",
                BigInteger.TWO, BigInteger.ONE,
                LocalDate.parse("2025-01-01"), LocalDate.parse("2024-12-31")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionCode.PERIOD_NOT_MATCHED.message);
    }
}