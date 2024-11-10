package store.controller;

import store.common.DateTimeGenerator;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

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
        //
    }
}
