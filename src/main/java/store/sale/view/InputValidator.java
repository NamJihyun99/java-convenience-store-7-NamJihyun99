package store.sale.view;

import store.domain.Product;
import store.sale.service.SaleService;

import java.math.BigInteger;
import java.util.List;

import static store.sale.common.InputValidationExceptionCode.INCORRECT_FORMAT;
import static store.sale.common.InputValidationExceptionCode.QUANTITY_EXCEED;

public class InputValidator {

    public static void validateYn(String response) {
        if (!response.equals("Y") && !response.equals("N")) {
            throw new IllegalArgumentException(INCORRECT_FORMAT.message);
        }
    }

    public static void validateOrderRequest(String orderRequest, SaleService saleService) {
        checkBlank(orderRequest);
        List.of(orderRequest.split(",")).forEach(orderInput -> {
            validateHeadTail(orderInput);
            List<String> order = List.of(orderInput.substring(1, orderInput.length() - 1).split("-"));
            validateDemand(order);
            validateQuantityExceed(new BigInteger(order.get(1)), saleService.findProductByName(order.getFirst()));
        });

    }

    private static void validateDemand(List<String> order) {
        try {
            new BigInteger(order.get(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INCORRECT_FORMAT.message);
        }
    }

    private static void checkBlank(String orderRequest) {
        if (orderRequest == null || orderRequest.isBlank()) {
            throw new IllegalArgumentException(INCORRECT_FORMAT.message);
        }
    }

    private static void validateQuantityExceed(BigInteger demandAmount, Product product) {
        BigInteger quantity = product.quantity();
        if (product.getPromotionInventory().isPresent()) {
            quantity = quantity.add(product.getPromotionInventory().get().quantity());
        }
        if (quantity.compareTo(demandAmount) < 0) {
            throw new IllegalArgumentException(QUANTITY_EXCEED.message);
        }
    }

    private static void validateHeadTail(String orderInput) {
        if (orderInput.charAt(0) != '[' || orderInput.charAt(orderInput.length()-1) != ']') {
            throw new IllegalArgumentException(INCORRECT_FORMAT.message);
        }
    }
}
