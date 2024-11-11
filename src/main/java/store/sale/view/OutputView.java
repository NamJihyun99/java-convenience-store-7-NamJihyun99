package store.sale.view;

import store.domain.Product;
import store.sale.dto.ProductResponseDto;
import store.sale.dto.ReceiptDto;

import java.util.Collection;
import java.util.List;

public class OutputView {

    private static final String HEADER = "[ERROR] ";

    public void printError(Exception e) {
        System.out.println(HEADER + e.getMessage() + "\n");
    }

    public void printProducts(List<Product> products) {
        StringBuilder builder = new StringBuilder().append(PromptMessage.shopMessage()).append("\n\n");

        products.stream()
                .map(ProductResponseDto::create)
                .flatMap(Collection::stream)
                .forEach(responseDto -> builder.append(responseDto.toString()).append("\n"));
        System.out.println(builder);
    }

    public void printReceipt(ReceiptDto receipt) {
        System.out.println(receipt);
    }
}
