package store.view;

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

        Product product = new Product("사이다");
        Promotion bngoPromotion = BuyNGetOneFree.create("탄산2+1",
                BigInteger.TWO, BigInteger.ONE,
                LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31")
        );
        Promotion nonePromotion = new NonePromotion();
        product.addInventory(new Inventory(1000L, new BigInteger("8"), bngoPromotion));
        product.addInventory(new Inventory(1000L, new BigInteger("7"), nonePromotion));
        List<ProductResponseDto> responseDtos = ProductResponseDto.create(product);
        for (ProductResponseDto responseDto : responseDtos) {
            System.out.println(responseDto);
        }
        assertThat(responseDtos.size()).isEqualTo(2);
        assertThat(responseDtos.get(0).toString()).isEqualTo("- 사이다 1,000원 8개 탄산2+1 ");
        assertThat(responseDtos.get(1).toString()).isEqualTo("- 사이다 1,000원 7개  ");
    }
}