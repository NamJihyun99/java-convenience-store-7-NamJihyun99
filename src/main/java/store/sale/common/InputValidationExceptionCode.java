package store.sale.common;

public enum InputValidationExceptionCode {

    INCORRECT_FORMAT("잘못된 입력입니다. 다시 입력해 주세요."),
    QUANTITY_EXCEED("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    ORDER_NOT_MATCHED("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");

    public final String message;

    InputValidationExceptionCode(String message) {
        this.message = message;
    }
}
