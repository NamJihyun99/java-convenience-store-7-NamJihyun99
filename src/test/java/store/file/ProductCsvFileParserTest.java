package store.file;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.file.dto.ProductSaveDto;

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

    @DisplayName("products.md 파일 상품목록의 프로모션이 'null'인 경우 null을 할당한다")
    @Test
    void 상품_프로모션_null_할당() throws Exception {
        ProductCsvFileParser parser = new ProductCsvFileParser();
        List<ProductSaveDto> result = parser.parse("src/test/resources/products.md");
        assertThat(result.get(1).promotion).isNull();
    }

    @DisplayName("products.md 파일 상품목록의 price의 타입은 BigInteger다")
    @Test
    void 상품_가격_타입() throws Exception {
        ProductCsvFileParser parser = new ProductCsvFileParser();
        List<ProductSaveDto> result = parser.parse("src/test/resources/products.md");
        assertThat(result.getFirst().price).isInstanceOf(BigInteger.class);
    }

    @DisplayName("products.md 파일 상품목록의 quantity의 타입은 BigInteger다")
    @Test
    void 상품_재고_타입() throws Exception {
        ProductCsvFileParser parser = new ProductCsvFileParser();
        List<ProductSaveDto> result = parser.parse("src/test/resources/products.md");
        assertThat(result.getFirst().quantity).isInstanceOf(BigInteger.class);
    }

}