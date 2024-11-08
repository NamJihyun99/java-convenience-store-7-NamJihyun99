package store.common;

public enum ExceptionCode {

    QUANTITY_SHORTAGE("재고가 부족합니다");

    public final String message;

    ExceptionCode(String message) {
        this.message = message;
    }
}
