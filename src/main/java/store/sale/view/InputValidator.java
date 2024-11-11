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
        List<String> productOrders = List.of(orderRequest.split(","));
        try {
            productOrders.forEach(orderInput -> {
                validateProductForm(orderInput);
                List<String> order = List.of(orderInput.substring(1, orderInput.length() - 1).split("-"));
                validateQuantityExceed(order, saleService.findProductByName(order.getFirst()));
            });
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private static void validateQuantityExceed(List<String> order, Product product) {
        BigInteger quantity = product.quantity();
        if (product.getPromotionInventory().isPresent()) {
            quantity = quantity.add(product.getPromotionInventory().get().quantity());
        }
        BigInteger demandAmount = new BigInteger(order.getLast());
        if (quantity.compareTo(demandAmount) < 0) {
            throw new IllegalArgumentException(QUANTITY_EXCEED.message);
        }
    }

    private static void validateProductForm(String orderInput) {
        if (orderInput.charAt(0) != '[' || orderInput.charAt(orderInput.length()-1) != ']') {
            throw new IllegalArgumentException(INCORRECT_FORMAT.message);
        }
    }
}
