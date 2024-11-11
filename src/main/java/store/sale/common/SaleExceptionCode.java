package store.sale.common;

public enum SaleExceptionCode {

    PRODUCT_NOT_FOUND("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    PROMOTION_NOT_FOUND("해당 이름의 프로모션을 찾을 수 없습니다"),
    PROMOTION_NOT_EXISTED("해당 상품에 적용된 프로모션이 없습니다"),
    PROMOTION_UNABLE("해당 프로모션을 적용할 수 없습니다"),
    AMOUNT_TOO_BIG("값이 너무 커서 뺄 수 없습니다");

    public final String message;

    SaleExceptionCode(String message) {
        this.message = message;
    }
}
