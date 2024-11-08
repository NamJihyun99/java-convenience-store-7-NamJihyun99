package store.file.dto;

import store.file.exception.ExceptionCode;

import java.util.List;

public class ProductSaveDto implements SaveDto {

    private final String name;
    private final String price;
    private final String quantity;
    private final String promotion;

    public static final int numberOfFields = 4;

    private ProductSaveDto(String name, String price, String quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public static ProductSaveDto create(List<String> params) {
        validateSize(params);
        return new ProductSaveDto(params.get(0), params.get(1), params.get(2), params.get(3));
    }

    private static void validateSize (List<String> params) {
        if (params.size() != numberOfFields) {
            throw new IllegalArgumentException(ExceptionCode.DTO_NOT_MATCHED.message);
        }
    }

    @Override
    public String toString() {
        return "ProductSaveDto{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", quantity='" + quantity + '\'' +
                ", promotion='" + promotion + '\'' +
                '}';
    }
}
