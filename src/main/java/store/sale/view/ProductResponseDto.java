package store.sale.view;

import store.domain.Product;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductResponseDto {

    private final String name;
    private final Long price;
    private final BigInteger quantity;
    private final String promotion;

    private ProductResponseDto(String name, Long price, BigInteger quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = "";
    }

    private ProductResponseDto(String name, Long price, BigInteger quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public static List<ProductResponseDto> create(Product product) {
        List<ProductResponseDto> dtos = new ArrayList<>();
        product.getPromotionInventory().ifPresent(inventory ->
                dtos.add(new ProductResponseDto(product.name(), product.price(), inventory.quantity(), inventory.promotion().name()))
        );
        dtos.add(new ProductResponseDto(product.name(), product.price(), product.quantity(), ""));
        return dtos;
    }

    // TODO: 함수 예쁘게 만들기
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("###,###");
        StringBuilder builder = new StringBuilder("- ");
        builder.append(name).append(" ")
                .append(df.format(price)).append("원 ");
        if (quantity.compareTo(BigInteger.ZERO) == 0) {
            builder.append("재고 없음");
        } else {
            builder.append(df.format(quantity)).append("개 ");
        }
        builder.append(promotion).append(" ");
        return builder.toString();
    }
}
