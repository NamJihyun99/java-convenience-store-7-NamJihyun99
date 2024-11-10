package store.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Promotion;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryPromotionRepositoryTest {

    MemoryPromotionRepository repository;

    @BeforeEach
    void setUp() {
        repository = MemoryPromotionRepository.getInstance();
    }

    @AfterEach
    void tearDown() {
        repository.clear();
    }

    @DisplayName("프로모션을 Map에 저장한다")
    @Test
    void save() {
        Promotion promotion = Promotion.create("탄산2+1",
                BigInteger.TWO, BigInteger.ONE,
                LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"));
        Promotion saved = repository.save(promotion);
        assertThat(repository.count()).isEqualTo(1);
    }

    @DisplayName("Map에서 이름으로 프로모션을 찾는다")
    @Test
    void findByName() {
        String name = "탄산2+1";
        Promotion promotion = Promotion.create(name,
                BigInteger.TWO, BigInteger.ONE,
                LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"));
        Promotion saved = repository.save(promotion);
        assertThat(repository.findByName(name).get()).isEqualTo(saved);
    }
}