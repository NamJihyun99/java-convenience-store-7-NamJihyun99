package store.domain;

import java.math.BigInteger;
import java.time.LocalDate;

import static store.common.ExceptionCode.*;

public class BuyNGetOneFree extends Promotion {

    private final BigInteger get;
    private final BigInteger buy;
    private final LocalDate startDate;
    private final LocalDate endDate;


    private BuyNGetOneFree(String name, BigInteger get, BigInteger buy, LocalDate startDate, LocalDate endDate) {
        super(name);
        this.get = get;
        this.buy = buy;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static BuyNGetOneFree create(String name, BigInteger get, BigInteger buy, LocalDate startDate, LocalDate endDate) {
        validateName(name);
        validateRule(get, buy);
        validateDate(startDate, endDate);
        return new BuyNGetOneFree(name, get, buy, startDate, endDate);
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(NAME_BLANK.message);
        }
    }

    private static void validateRule(BigInteger get, BigInteger buy) {
        if (get == null || buy == null || get.compareTo(BigInteger.ZERO) <= 0 || buy.compareTo(BigInteger.ZERO) <= 0) {
            throw new IllegalArgumentException(NUMBER_NEGATIVE.message);
        }
    }

    private static void validateDate(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(PERIOD_NOT_MATCHED.message);
        }
    }
}
