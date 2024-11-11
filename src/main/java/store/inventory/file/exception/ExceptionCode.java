package store.inventory.file.exception;

public enum ExceptionCode {

    NO_FIELDS("파일의 첫번째 줄은 필드 정보가 제시되어야 합니다"),
    DTO_NOT_MATCHED("DTO의 필드 개수와 일치하지 않습니다"),
    FIELD_TYPE_NOT_MATCHED("파일 입력과 필드의 타입이 맞지 않습니다");

    public final String message;

    ExceptionCode(String message) {
        this.message = message;
    }
}
