package store.inventory.controller;

import store.inventory.service.ProductService;
import store.inventory.view.FileInputView;

import java.io.IOException;

public class ProductController {

    private final FileInputView fileInputView;
    private final ProductService productService;

    private ProductController(FileInputView fileInputView, ProductService productService) {
        this.fileInputView = fileInputView;
        this.productService = productService;
    }

    public static ProductController create(FileInputView fileInputView, ProductService productService) {
        return new ProductController(fileInputView, productService);
    }

    public void run() throws IOException {
        productService.savePromotions(fileInputView.readPromotions());
        productService.saveProducts(fileInputView.readProducts());
    }
}
