package store;

import store.sale.common.DateTimeGenerator;
import store.sale.common.FixedDateTimeGenerator;
import store.inventory.controller.ProductController;
import store.sale.controller.SaleController;
import store.inventory.file.ProductCsvFileParser;
import store.inventory.file.PromotionCsvFileParser;
import store.inventory.service.ProductService;
import store.sale.service.SaleService;
import store.sale.view.ConsoleInputView;
import store.inventory.view.FileInputView;
import store.sale.view.OutputView;

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
        SaleService service = new SaleService();
        DateTimeGenerator dateTimeGenerator = new FixedDateTimeGenerator();
        SaleController controller = SaleController.create(consoleInputView, outputView, service, dateTimeGenerator);
        controller.run();
    }

    public static void main(String[] args) {
        setStore();
        runStore();
    }
}
