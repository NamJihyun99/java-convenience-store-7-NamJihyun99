package store.inventory.view;

import store.inventory.file.ProductCsvFileParser;
import store.inventory.file.PromotionCsvFileParser;
import store.inventory.dto.dto.ProductSaveDto;
import store.inventory.dto.dto.PromotionSaveDto;

import java.io.IOException;
import java.util.List;

public class FileInputView {

    private final ProductCsvFileParser  productParser;
    private final PromotionCsvFileParser promotionParser;

    private static final String PRODUCT_FILE_PATH = "src/main/resources/products.md";
    private static final String PROMOTION_FILE_PATH = "src/main/resources/promotions.md";

    public FileInputView(ProductCsvFileParser productParser, PromotionCsvFileParser promotionParser) {
        this.productParser = productParser;
        this.promotionParser = promotionParser;
    }

    public List<ProductSaveDto> readProducts() throws IOException {
        return productParser.parse(PRODUCT_FILE_PATH);
    }

    public List<PromotionSaveDto> readPromotions() throws IOException {
        return promotionParser.parse(PROMOTION_FILE_PATH);
    }
}
