package store.inventory.file.dto;

import store.domain.Inventory;
import store.domain.Product;
import store.domain.Promotion;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static store.inventory.file.exception.ExceptionCode.DTO_NOT_MATCHED;
import static store.inventory.file.exception.ExceptionCode.FIELD_TYPE_NOT_MATCHED;

public class ProductSaveDto implements SaveDto {

    public final String name;
    public final Long price;
    public final BigInteger quantity;
    public final String promotion;

    public static final int numberOfFields = 4;

    private ProductSaveDto(String name, Long price, BigInteger quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public static ProductSaveDto create(List<String> params) {
        validateParams(params);
        String name = params.get(0);
        Long price = Long.valueOf(params.get(1));
        BigInteger quantity = new BigInteger(params.get(2));
        String promotion = params.get(3);
        return new ProductSaveDto(name, price, quantity, promotion);
    }

    private static void validateParams(List<String> params) {
        validateSize(params);
        validateString(params.get(0));
        validateNumber(params.get(1));
        validateNumber(params.get(2));
        validateString(params.get(3));
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

    private static void validateSize (List<String> params) {
        if (params.size() != numberOfFields) {
            throw new IllegalArgumentException(DTO_NOT_MATCHED.message);
        }
    }

    public static Product createProduct(Optional<Product> result, ProductSaveDto dto, Promotion promotion) {
        Product product = result.orElse(new Product(dto.name));
        product.addInventory(new Inventory(dto.price, dto.quantity, promotion));
        return product;
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
