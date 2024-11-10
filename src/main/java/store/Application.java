package store;

import store.common.DateTimeGenerator;
import store.common.FixedDateTimeGenerator;
import store.controller.StoreController;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        StoreService service = new StoreService();
        DateTimeGenerator dateTimeGenerator = new FixedDateTimeGenerator();
        StoreController controller = StoreController.create(inputView, outputView, service, dateTimeGenerator);
        controller.run();
    }
}
