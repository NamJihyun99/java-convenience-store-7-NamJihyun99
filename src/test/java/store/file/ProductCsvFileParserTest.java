package store.file;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.file.dto.ProductSaveDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductCsvFileParserTest {

    @DisplayName("products.md 파일의 상품목록을 읽어와 DTO로 변환한다")
    @Test
    void 파일_파싱_성공() throws Exception {
        ProductCsvFileParser parser = new ProductCsvFileParser();
        List<ProductSaveDto> result = parser.parse("src/test/resources/products.md");
        assertThat(result.size()).isEqualTo(5);
        result.forEach(System.out::println);
    }
}