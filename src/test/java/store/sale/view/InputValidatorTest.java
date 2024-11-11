package store.sale.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import store.repository.MemoryProductRepository;
import store.sale.service.SaleService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static store.sale.common.InputValidationExceptionCode.INCORRECT_FORMAT;

class InputValidatorTest {

    @ParameterizedTest
    @DisplayName("Y 또는 N이 아닐 경우 예외가 발생한다")
    @ValueSource(strings = {"", "  ", "helloWorld", "yn"})
    void validateYn(String input) {
        assertThatThrownBy(() -> InputValidator.validateYn(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INCORRECT_FORMAT.message);
    }

    @ParameterizedTest
    @DisplayName("입력값이 공백이거나 잘못된 형식일 경우 예외가 발생한다")
    @ValueSource(strings = {"", "  ", "helloWorld", "[사이다-10]/[콜라-2]", "사이다-10,콜라-3"})
    void validateOrderRequest(String input) {
        SaleService saleService = new SaleService(MemoryProductRepository.getInstance());
        assertThatThrownBy(() -> InputValidator.validateOrderRequest(input, saleService))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INCORRECT_FORMAT.message);
    }
}