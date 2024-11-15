package store.sale.view;

import org.junit.jupiter.api.Test;
import store.domain.Inventory;
import store.domain.Product;
import store.domain.Promotion;
import store.sale.dto.ProductResponseDto;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductResponseDtoTest {

    @Test
    void 재고_출력_형식_검증() {
        Promotion promotion = Promotion.create("탄산2+1",
                BigInteger.TWO, BigInteger.ONE,
                LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31")
        );
        Product product = new Product("사이다", 1000L);
        product.setQuantity(BigInteger.valueOf(7));
        product.setPromotionInventory(new Inventory(BigInteger.valueOf(8), promotion));

        List<ProductResponseDto> responseDtos = ProductResponseDto.create(product);

        assertThat(responseDtos.size()).isEqualTo(2);
        assertThat(responseDtos.get(0).toString()).isEqualTo("- 사이다 1,000원 8개 탄산2+1 ");
        assertThat(responseDtos.get(1).toString()).isEqualTo("- 사이다 1,000원 7개  ");
    }
}