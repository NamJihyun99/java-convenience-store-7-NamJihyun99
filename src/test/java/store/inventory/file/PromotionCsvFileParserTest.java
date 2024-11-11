package store.inventory.file;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.inventory.dto.dto.PromotionSaveDto;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PromotionCsvFileParserTest {

    static final String FILE_PATH = "src/test/resources/promotions.md";

    @DisplayName("promotions.md 파일의 프로모션 목록을 읽어와 DTO로 변환한다")
    @Test
    void 파일_파싱_성공() throws Exception {
        PromotionCsvFileParser parser = new PromotionCsvFileParser();
        List<PromotionSaveDto> result = parser.parse(FILE_PATH);
        result.forEach(System.out::println);
        assertThat(result.size()).isEqualTo(3);
    }

    @DisplayName("products.md 파일 상품목록의 date 타입은 LocalDate이다")
    @Test
    void date_타입() throws Exception {
        PromotionCsvFileParser parser = new PromotionCsvFileParser();
        List<PromotionSaveDto> result = parser.parse(FILE_PATH);
        assertThat(result.getFirst().startDate).isInstanceOf(LocalDate.class);
        assertThat(result.getFirst().endDate).isInstanceOf(LocalDate.class);
    }
}