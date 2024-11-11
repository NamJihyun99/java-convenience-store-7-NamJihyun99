package store.inventory.file;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.inventory.dto.dto.ProductSaveDto;

import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductCsvFileParserTest {

    @DisplayName("products.md 파일의 상품목록을 읽어와 DTO로 변환한다")
    @Test
    void 파일_파싱_성공() throws Exception {
        ProductCsvFileParser parser = new ProductCsvFileParser();
        List<ProductSaveDto> result = parser.parse("src/test/resources/products.md");
        result.forEach(System.out::println);
        assertThat(result.size()).isEqualTo(5);
    }

    @DisplayName("products.md 파일 상품목록의 price의 타입은 Long이다")
    @Test
    void 상품_가격_타입() throws Exception {
        ProductCsvFileParser parser = new ProductCsvFileParser();
        List<ProductSaveDto> result = parser.parse("src/test/resources/products.md");
        assertThat(result.getFirst().price).isInstanceOf(Long.class);
    }

    @DisplayName("products.md 파일 상품목록의 quantity의 타입은 BigInteger다")
    @Test
    void 상품_재고_타입() throws Exception {
        ProductCsvFileParser parser = new ProductCsvFileParser();
        List<ProductSaveDto> result = parser.parse("src/test/resources/products.md");
        assertThat(result.getFirst().quantity).isInstanceOf(BigInteger.class);
    }

}