package store.sale.controller;

import store.sale.common.DateTimeGenerator;
import store.sale.service.SaleService;
import store.sale.view.ConsoleInputView;
import store.sale.view.OutputView;

import java.util.function.Supplier;

import static store.sale.common.InputValidationExceptionCode.INCORRECT_FORMAT;

public class SaleController {

    private final ConsoleInputView consoleInputView;
    private final OutputView outputView;
    private final SaleService saleService;
    private final DateTimeGenerator dateTimeGenerator;

    private SaleController(ConsoleInputView consoleInputView,
                           OutputView outputView,
                           SaleService saleService,
                           DateTimeGenerator dateTimeGenerator) {
        this.consoleInputView = consoleInputView;
        this.outputView = outputView;
        this.saleService = saleService;
        this.dateTimeGenerator = dateTimeGenerator;
    }

    public static SaleController create(ConsoleInputView consoleInputView, OutputView outputView, SaleService saleService, DateTimeGenerator dateTimeGenerator) {
        return new SaleController(consoleInputView, outputView, saleService, dateTimeGenerator);
    }

    public void run() {
        do {
            //
        } while (readContinueYn().equals("Y"));
    }

    private String readContinueYn() {
        return retryUntilValid(() -> {
            String continueYn = consoleInputView.readNextTurnYn();
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
