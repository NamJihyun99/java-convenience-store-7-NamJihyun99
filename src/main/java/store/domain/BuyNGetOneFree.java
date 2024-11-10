package store.domain;

import java.math.BigInteger;
import java.time.LocalDate;

import static store.inventory.common.ExceptionCode.*;

public class BuyNGetOneFree extends Promotion {

    private final BigInteger buy;
    private final BigInteger get;
    private final LocalDate startDate;
    private final LocalDate endDate;


    private BuyNGetOneFree(String name, BigInteger buy, BigInteger get, LocalDate startDate, LocalDate endDate) {
        super(name);
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static BuyNGetOneFree create(String name, BigInteger buy, BigInteger get, LocalDate startDate, LocalDate endDate) {
        validateName(name);
        validateRule(buy, get);
        validateDate(startDate, endDate);
        return new BuyNGetOneFree(name, get, buy, startDate, endDate);
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(NAME_BLANK.message);
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
}
