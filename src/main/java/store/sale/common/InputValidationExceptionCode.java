package store.sale.common;

public enum InputValidationExceptionCode {

    INCORRECT_FORMAT("잘못된 입력입니다. 다시 입력해 주세요.");

    public final String message;

    InputValidationExceptionCode(String message) {
        this.message = message;
    }
}
