package store.controller;

import store.file.dto.ProductSaveDto;
import store.service.ProductService;
import store.view.FileInputView;

import java.io.IOException;
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
    }
}
