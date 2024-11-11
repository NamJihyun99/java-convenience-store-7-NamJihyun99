package store.sale.controller;

import store.sale.common.DateTime;
import store.sale.domain.Order;
import store.sale.model.PurchasingPlan;
import store.sale.service.SaleService;
import store.sale.view.*;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Supplier;

import static store.sale.common.InputValidationExceptionCode.INCORRECT_FORMAT;

public class SaleController {

    private final ConsoleInputView inputView;
    private final OutputView outputView;
    private final SaleService saleService;
    private final DateTime dateTime;

    private SaleController(ConsoleInputView consoleInputView,
                           OutputView outputView,
                           SaleService saleService,
                           DateTime dateTime) {
        this.inputView = consoleInputView;
        this.outputView = outputView;
        this.saleService = saleService;
        this.dateTime = dateTime;
    }

    public static SaleController create(ConsoleInputView consoleInputView, OutputView outputView, SaleService saleService, DateTime dateTime) {
        return new SaleController(consoleInputView, outputView, saleService, dateTime);
    }

    public void run() {
        do {
            outputView.printProducts(saleService.readProducts());
            List<List<String>> tokens = OrderRequestParser.parse(readOrderRequest());
            List<Order> orders = saleService.createOrders(tokens);
            PurchasingPlan plan = new PurchasingPlan(dateTime, orders);
            List<ProductAmountDto> extraGets = saleService.getEnableProduct(orders, dateTime);
            for (ProductAmountDto dto : extraGets) {
                String response = readExtraGet(dto);
                if (response.equals("Y")) {
                    plan.addFreeGet(dto);
                }
            }
            plan.promotionQuantityShortages().forEach(dto -> {
                if (readNonPromotion(dto).equals("N")) {
                    plan.subtractNonPromotions(dto.name());
                }
            });
            if (readMembership().equals("Y")) {
                plan.applyMembership();
            }
            outputView.printReceipt(makeReceipt(plan));
            // 재고 삭감
        } while (readContinueYn().equals("Y"));
    }

    private ReceiptDto makeReceipt(PurchasingPlan plan) {
        List<ProductAmountPriceDto> buys = plan.getForms().values().stream().map(form ->
                new ProductAmountPriceDto(form.getProduct().name(),
                        form.getPayedAmount(),
                        form.getPayedAmount().multiply(BigInteger.valueOf(form.getProduct().price()))
                )
        ).toList();
        List<ProductAmountDto> gets = plan.getForms().values().stream().map(form ->
                new ProductAmountDto(form.getProduct().name(),
                        form.getUnpayedAmount()
                )
        ).toList();
        return new ReceiptDto(buys, gets, plan.getTotal(), plan.getGetTotal(), plan.getMembershipDiscount());
    }

    private String readExtraGet(ProductAmountDto extraGet) {
        return retryUntilValid(() -> {
            String response = inputView.readPromotionYn(extraGet);
            validateYn(response);
            return response;
        });
    }

    private String readOrderRequest() {
        return retryUntilValid(() -> {
            String orderRequest = inputView.readOrderRequest();
            validateOrderRequest(orderRequest);
            return orderRequest;
        });
    }

    private String readNonPromotion(ProductAmountDto shortage) {
        return retryUntilValid(() -> {
            String response = inputView.readNonPromotionYn(shortage.name(), shortage.amount());
            validateYn(response);
            return response;
        });
    }

    private String readMembership() {
        return retryUntilValid(() -> {
            String response = inputView.readMembershipYn();
            validateYn(response);
            return response;
        });
    }

    // TODO: 입력값 유효성 검사 책임 분리하기
    private void validateOrderRequest(String orderRequest) {
        List<String> orderInputs = List.of(orderRequest.split(","));
        try {
            orderInputs.forEach(orderInput -> {
                validateProductForm(orderInput);
                new BigInteger(orderInput.substring(1, orderInput.length()-1).split("-")[1]);
            });
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(INCORRECT_FORMAT.message);
        }
    }

    private static void validateProductForm(String orderInput) {
        if (orderInput.charAt(0) != '[' || orderInput.charAt(orderInput.length()-1) != ']') {
            throw new IllegalArgumentException(INCORRECT_FORMAT.message);
        }
    }

    private String readContinueYn() {
        return retryUntilValid(() -> {
            String continueYn = inputView.readNextTurnYn();
            validateYn(continueYn);
            return continueYn;
        });
    }

    private void validateYn(String response) {
        if (!response.equals("Y") && !response.equals("N")) {
            throw new IllegalArgumentException(INCORRECT_FORMAT.message);
        }
    }

    private <T> T retryUntilValid(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            outputView.printError(e);
            return retryUntilValid(supplier);
        }
    }
}
