package store.domain;

import java.math.BigInteger;

public class NonePromotion {

    private final String name;
    private final BigInteger buy;
    private final BigInteger get;

    private NonePromotion() {
        this.name = "";
        this.buy = BigInteger.ONE;
        this.get = BigInteger.ZERO;
    }

    public static NonePromotion getInstance() {
        return new NonePromotion();
    }

    @Override
    public String toString() {
        return "";
    }
}
