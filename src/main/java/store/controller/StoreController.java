package store.controller;

import store.common.DateTimeGenerator;
import store.file.dto.ProductSaveDto;
import store.service.ProductService;
import store.service.StoreService;
import store.view.ConsoleInputView;
import store.view.FileInputView;
import store.view.OutputView;

import java.util.List;
import java.util.function.Supplier;

import static store.common.InputValidationExceptionCode.INCORRECT_FORMAT;

public class StoreController {

    private final ConsoleInputView consoleInputView;
    private final OutputView outputView;
    private final StoreService storeService;
    private final DateTimeGenerator dateTimeGenerator;

    private StoreController(ConsoleInputView consoleInputView,
                            OutputView outputView,
                            StoreService storeService,
                            DateTimeGenerator dateTimeGenerator) {
        this.consoleInputView = consoleInputView;
        this.outputView = outputView;
        this.storeService = storeService;
        this.dateTimeGenerator = dateTimeGenerator;
    }

    public static StoreController create(ConsoleInputView consoleInputView, OutputView outputView, StoreService storeService, DateTimeGenerator dateTimeGenerator) {
        return new StoreController(consoleInputView, outputView, storeService, dateTimeGenerator);
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
