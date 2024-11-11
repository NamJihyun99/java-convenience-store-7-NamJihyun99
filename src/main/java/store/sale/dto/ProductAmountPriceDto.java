package store.sale.dto;

import java.math.BigInteger;

public record ProductAmountPriceDto(String name, BigInteger amount, BigInteger totalPrice) {
}
