package store.sale.domain;

import store.domain.Inventory;
import store.domain.Product;
import store.domain.Promotion;
import store.sale.view.ProductAmountDto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public record Order(Product product, BigInteger amount) {

    public List<ProductAmountDto> getExtraGet() {
        List<Inventory> inventories = product.getPromotionInventory().stream().toList();
        if (inventories.isEmpty()) {
            return List.of();
        }
        return calculateGets(inventories.getFirst());
    }

    private List<ProductAmountDto> calculateGets(Inventory inventory) {
        List<ProductAmountDto> result = new ArrayList<>();
        Promotion promotion = inventory.promotion();
        BigInteger bundle = promotion.buy().add(promotion.get());
        BigInteger count = BigInteger.ZERO;
        for (BigInteger number = bundle; number.compareTo(inventory.quantity()) <= 0; number = number.add(bundle)) {
            count = count.add(BigInteger.ONE);
        }
        result.add(new ProductAmountDto(product().name(), count));
        return result;
    }
}
