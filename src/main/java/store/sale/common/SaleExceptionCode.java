package store.sale.common;

public enum SaleExceptionCode {

    PRODUCT_NOT_FOUND("해당 이름의 상품을 찾을 수 없습니다"),
    PROMOTION_NOT_FOUND("해당 이름의 프로모션을 찾을 수 없습니다"),
    PROMOTION_NOT_EXISTED("해당 상품에 적용된 프로모션이 없습니다"),
    PROMOTION_UNABLE("해당 프로모션을 적용할 수 없습니다");

    public final String message;

    SaleExceptionCode(String message) {
        this.message = message;
    }
}
