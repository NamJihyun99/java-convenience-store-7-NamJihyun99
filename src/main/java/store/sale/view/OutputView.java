package store.sale.view;

import store.domain.Product;

import java.util.Collection;
import java.util.List;

public class OutputView {

    private static final String HEADER = "[ERROR] ";

    public void printError(Exception e) {
        System.out.println(HEADER + e.getMessage());
    }

    public void printProducts(List<Product> products) {
        StringBuilder builder = new StringBuilder();
        builder.append(PromptMessage.shopMessage()).append("\n\n");

        List<ProductResponseDto> responses = products.stream()
                .map(ProductResponseDto::create)
                .flatMap(Collection::stream)
                .toList();
        responses.forEach(responseDto -> builder.append(responseDto.toString()).append("\n"));
        System.out.println(builder);
    }
}
