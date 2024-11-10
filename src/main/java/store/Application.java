package store;

import store.inventory.controller.ProductController;
import store.inventory.file.ProductCsvFileParser;
import store.inventory.file.PromotionCsvFileParser;
import store.inventory.service.ProductService;
import store.inventory.view.FileInputView;
import store.repository.MemoryProductRepository;
import store.repository.MemoryPromotionRepository;
import store.sale.common.DateTimeGenerator;
import store.sale.common.FixedDateTimeGenerator;
import store.sale.controller.SaleController;
import store.sale.service.SaleService;
import store.sale.view.ConsoleInputView;
import store.sale.view.OutputView;

public class Application {

    private static void setStore() {
        FileInputView fileInputView = new FileInputView(new ProductCsvFileParser(), new PromotionCsvFileParser());
        ProductService productService = new ProductService(MemoryPromotionRepository.getInstance(), MemoryProductRepository.getInstance());
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
        SaleService service = new SaleService(MemoryProductRepository.getInstance(), MemoryPromotionRepository.getInstance());
        DateTimeGenerator dateTimeGenerator = new FixedDateTimeGenerator();
        SaleController controller = SaleController.create(consoleInputView, outputView, service, dateTimeGenerator);
        controller.run();
    }

    public static void main(String[] args) {
        setStore();
        runStore();
    }
}
