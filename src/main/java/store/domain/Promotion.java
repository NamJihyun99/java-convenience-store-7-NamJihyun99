package store.domain;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static store.inventory.common.ExceptionCode.*;

public class Promotion {

    private final String name;
    private final BigInteger buy;
    private final BigInteger get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private Promotion(String name, BigInteger buy, BigInteger get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Promotion create(String name, BigInteger buy, BigInteger get, LocalDate startDate, LocalDate endDate) {
        validateName(name);
        validateRule(buy, get);
        validateDate(startDate, endDate);
        return new Promotion(name, buy, get, startDate, endDate);
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank() || name.equals("null")) {
            throw new IllegalArgumentException(INCORRECT_NAME.message);
        }
    }

    private static void validateRule(BigInteger buy, BigInteger get) {
        if (get == null || buy == null || buy.compareTo(BigInteger.ZERO) <= 0 || get.compareTo(BigInteger.ZERO) <= 0) {
            throw new IllegalArgumentException(NUMBER_NEGATIVE.message);
        }
    }

    private static void validateDate(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(PERIOD_NOT_MATCHED.message);
        }
    }

    public String name() {
        return name;
    }

    public BigInteger buy() {
        return buy;
    }

    public BigInteger get() {
        return get;
    }

    public boolean enable(LocalDateTime now) {
        return now.isBefore(endDate.plusDays(1L).atStartOfDay())
                && now.isAfter(startDate.atStartOfDay());
    }
}
