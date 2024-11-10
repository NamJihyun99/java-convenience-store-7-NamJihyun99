package store;

import store.common.DateTimeGenerator;
import store.common.FixedDateTimeGenerator;
import store.controller.ProductController;
import store.controller.StoreController;
import store.file.ProductCsvFileParser;
import store.file.PromotionCsvFileParser;
import store.service.ProductService;
import store.service.StoreService;
import store.view.ConsoleInputView;
import store.view.FileInputView;
import store.view.OutputView;

public class Application {

    private static void setStore() {
        FileInputView fileInputView = new FileInputView(new ProductCsvFileParser(), new PromotionCsvFileParser());
        ProductService productService = new ProductService();
        ProductController controller = ProductController.create(fileInputView, productService);
        try {
            controller.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void runStore() {
        ConsoleInputView consoleInputView = new ConsoleInputView();
        OutputView outputView = new OutputView();
        StoreService service = new StoreService();
        DateTimeGenerator dateTimeGenerator = new FixedDateTimeGenerator();
        StoreController controller = StoreController.create(consoleInputView, outputView, service, dateTimeGenerator);
        controller.run();
    }

    public static void main(String[] args) {
        setStore();
        runStore();
    }
}
