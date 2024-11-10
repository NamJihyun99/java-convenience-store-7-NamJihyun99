package store.sale.view;

import org.junit.jupiter.api.Test;
import store.domain.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductResponseDtoTest {

    @Test
    void 재고_출력_형식_검증() {

        /**
         * - 사이다 1,000원 8개 탄산2+1
         * - 사이다 1,000원 7개
         * */
        Promotion bngoPromotion = BuyNGetOneFree.create("탄산2+1",
                BigInteger.TWO, BigInteger.ONE,
                LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31")
        );
        Promotion nonePromotion = NonePromotion.getInstance();
        Product product = new Product("사이다", new Inventory(1000L, BigInteger.valueOf(7), nonePromotion));
        product.addInventory(new Inventory(1000L, new BigInteger("8"), bngoPromotion));
        List<ProductResponseDto> responseDtos = ProductResponseDto.create(product);
        responseDtos.forEach(System.out::println);
        assertThat(responseDtos.size()).isEqualTo(2);
        assertThat(responseDtos.get(0).toString()).isEqualTo("- 사이다 1,000원 7개  ");
        assertThat(responseDtos.get(1).toString()).isEqualTo("- 사이다 1,000원 8개 탄산2+1 ");
    }
}