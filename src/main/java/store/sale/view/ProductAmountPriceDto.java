package store.sale.view;

import java.math.BigInteger;

public record ProductAmountPriceDto(String name, BigInteger amount, BigInteger totalPrice) {
}
