package store.inventory.common;

public enum ExceptionCode {

    QUANTITY_SHORTAGE("재고가 부족합니다"),
    NAME_BLANK("이름이 공백일 수 없습니다"),
    NUMBER_NEGATIVE("상품은 0개 이하일 수 없습니다"),
    PERIOD_NOT_MATCHED("기간의 종료 날짜는 시작 날짜보다 앞설 수 없습니다"),
    PROMOTION_EXCEED("적용 가능한 프로모션 개수를 초과하였습니다");

    public final String message;

    ExceptionCode(String message) {
        this.message = message;
    }
}
