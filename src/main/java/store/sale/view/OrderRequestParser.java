package store.sale.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderRequestParser {

    public static List<List<String>> parse(String ordersInput) {
        List<List<String>> result = new ArrayList<>();
        Arrays.stream(ordersInput.split(","))
                .forEach(order ->
                        result.add(List.of(order.substring(1, order.length() - 1).split("-")))
                );
        return result;
    }
}
