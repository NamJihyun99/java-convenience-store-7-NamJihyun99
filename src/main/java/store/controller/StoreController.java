package store.controller;

import store.common.DateTimeGenerator;
import store.common.InputValidationExceptionCode;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

import java.util.function.Supplier;

import static store.common.InputValidationExceptionCode.INCORRECT_FORMAT;

public class StoreController {

    private final InputView inputView;
    private final OutputView outputView;
    private final StoreService storeService;
    private final DateTimeGenerator dateTimeGenerator;

    private StoreController(InputView inputView, OutputView outputView, StoreService storeService, DateTimeGenerator dateTimeGenerator) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.storeService = storeService;
        this.dateTimeGenerator = dateTimeGenerator;
    }

    public static StoreController create(InputView inputView, OutputView outputView, StoreService storeService, DateTimeGenerator dateTimeGenerator) {
        return new StoreController(inputView, outputView, storeService, dateTimeGenerator);
    }

    public void run() {
        do {
            //
        } while (readContinueYn().equals("Y"));
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
