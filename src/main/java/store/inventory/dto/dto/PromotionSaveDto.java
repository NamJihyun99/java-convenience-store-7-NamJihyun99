package store.inventory.dto.dto;

import store.domain.Promotion;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import static store.inventory.file.exception.ExceptionCode.FIELD_TYPE_NOT_MATCHED;

public class PromotionSaveDto implements SaveDto{

    public final String name;
    public final BigInteger buy;
    public final BigInteger get;
    public final LocalDate startDate;
    public final LocalDate endDate;

    public final static int numberOfFields = 5;

    private PromotionSaveDto(String name, BigInteger buy, BigInteger get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static PromotionSaveDto create(List<String> params) {
        validateParams(params);
        String name = params.get(0);
        BigInteger buy = new BigInteger(params.get(1));
        BigInteger get = new BigInteger(params.get(2));
        LocalDate startDate = LocalDate.parse(params.get(3));
        LocalDate endDate = LocalDate.parse(params.get(4));
        return new PromotionSaveDto(name, buy, get, startDate, endDate);
    }

    private static void validateParams(List<String> params) {
        validateSize(params);
        validateString(params.get(0));
        validateNumber(params.get(1));
        validateNumber(params.get(2));
        validateDate(params.get(3));
        validateDate(params.get(4));
    }

    private static void validateNumber(String number) {
        try {
            if (number == null || new BigInteger(number).compareTo(BigInteger.ZERO) < 0) {
                throw new IllegalArgumentException(FIELD_TYPE_NOT_MATCHED.message);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(FIELD_TYPE_NOT_MATCHED.message);
        }
    }

    private static void validateString(String stringInput) {
        if (stringInput == null || stringInput.isBlank()) {
            throw new IllegalArgumentException(FIELD_TYPE_NOT_MATCHED.message);
        }
    }

    private static void validateDate(String dateInput) {
        try {
            LocalDate.parse(dateInput);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(FIELD_TYPE_NOT_MATCHED.message);
        }
    }

    private static void validateSize(List<String> params) {
        if (params.size() != numberOfFields) {
            throw new IllegalArgumentException();
        }
    }

    public static Promotion of(PromotionSaveDto dto) {
        return Promotion.create(dto.name, dto.buy, dto.get, dto.startDate, dto.endDate);
    }

    @Override
    public String toString() {
        return "PromotionSaveDto{" +
                "name='" + name + '\'' +
                ", buy=" + buy +
                ", get=" + get +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
