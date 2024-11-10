package store.inventory.controller;

import store.inventory.file.dto.ProductSaveDto;
import store.inventory.service.ProductService;
import store.inventory.view.FileInputView;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

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
        List<ProductSaveDto> productSaveDtos = fileInputView.readProducts();
        BigInteger promotionCount = productService.savePromotions(fileInputView.readPromotions());
        BigInteger productCount = productService.saveProducts(fileInputView.readProducts());
    }
}
