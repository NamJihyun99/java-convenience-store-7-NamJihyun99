package store.sale.controller;

import store.sale.common.DateTime;
import store.sale.domain.Order;
import store.sale.dto.ProductAmountDto;
import store.sale.dto.ProductAmountPriceDto;
import store.sale.dto.ReceiptDto;
import store.sale.model.PurchasingPlan;
import store.sale.service.SaleService;
import store.sale.view.ConsoleInputView;
import store.sale.view.InputValidator;
import store.sale.view.OrderRequestParser;
import store.sale.view.OutputView;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

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

    public static SaleController create(ConsoleInputView consoleInputView,
                                        OutputView outputView,
                                        SaleService saleService,
                                        DateTime dateTime) {
        return new SaleController(consoleInputView, outputView, saleService, dateTime);
    }

    public void run() {
        do {
            outputView.printProducts(saleService.readProducts());
            List<Order> orders = createOrders();
            PurchasingPlan plan = new PurchasingPlan(dateTime, orders);
            process(orders, plan);
            outputView.printReceipt(makeReceipt(plan));
            saleService.deleteQuantity(plan);
        } while ("Y".equals(readWithValidation(inputView::readNextTurnYn, InputValidator::validateYn)));
    }

    private List<Order> createOrders() {
        return saleService.createOrders(OrderRequestParser.parse(
                readWithValidation(inputView::readOrderRequest,
                        orderRequest -> InputValidator.validateOrderRequest(orderRequest, saleService))
        ));
    }

    private void process(List<Order> orders, PurchasingPlan plan) {
        handleExtraGets(orders, plan);
        handleNonPromotions(plan);
        handleMembershipDiscount(plan);
    }

    private void handleMembershipDiscount(PurchasingPlan plan) {
        if ("Y".equals(readWithValidation(inputView::readMembershipYn, InputValidator::validateYn))) {
            plan.applyMembership();
        }
    }

    private void handleNonPromotions(PurchasingPlan plan) {
        plan.promotionQuantityShortages().forEach(dto -> {
            if ("N".equals(readWithValidation(() ->
                            inputView.readNonPromotionYn(dto.name(),
                                    dto.amount()),
                    InputValidator::validateYn))) {
                plan.subtractNonPromotions(dto.name());
            }
        });
    }

    private void handleExtraGets(List<Order> orders, PurchasingPlan plan) {
        saleService.getEnableProduct(orders, dateTime).forEach(dto -> {
            if ("Y".equals(readWithValidation(() ->
                    inputView.readPromotionYn(dto), InputValidator::validateYn
            ))) {
                plan.addFreeGet(dto);
            }
        });
    }

    private ReceiptDto makeReceipt(PurchasingPlan plan) {
        List<ProductAmountPriceDto> buys = getBuysDto(plan);
        List<ProductAmountDto> gets = getGetsDto(plan);
        return new ReceiptDto(buys, gets, plan.getTotal(), plan.getPromotionDiscount(), plan.getMembershipDiscount());
    }

    private static List<ProductAmountDto> getGetsDto(PurchasingPlan plan) {
        return plan.getForms().values().stream().
                map(form -> new ProductAmountDto(form.getProduct().name(), form.getUnpayedAmount()))
                .toList();
    }

    private static List<ProductAmountPriceDto> getBuysDto(PurchasingPlan plan) {
        return plan.getForms().values().stream()
                .map(form ->
                        new ProductAmountPriceDto(
                                form.getProduct().name(),
                                form.getPromotionBuyAmount().add(form.getNonPromotionAmount()),
                                form.getPromotionBuyAmount().add(form.getNonPromotionAmount()).multiply(BigInteger.valueOf(form.getProduct().price())))
                ).toList();
    }

    private <T> T readWithValidation(Supplier<T> supplier, Consumer<T> validator) {
        try {
            T result = supplier.get();
            validator.accept(result);
            return result;
        } catch (IllegalArgumentException e) {
            outputView.printError(e);
            return readWithValidation(supplier, validator);
        }
    }
}
