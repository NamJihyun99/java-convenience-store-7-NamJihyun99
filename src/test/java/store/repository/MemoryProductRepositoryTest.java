package store.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.BuyNGetOneFree;
import store.domain.Inventory;
import store.domain.Product;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryProductRepositoryTest {

    MemoryProductRepository repository;

    @BeforeEach
    void setUp() {
        repository = MemoryProductRepository.getInstance();
    }

    @AfterEach
    void tearDown() {
        repository.clear();
    }

    @DisplayName("상품을 Map에 저장한다")
    @Test
    void save() {
        BuyNGetOneFree promotion = BuyNGetOneFree.create("탄산2+1",
                BigInteger.TWO, BigInteger.ONE,
                LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"));
        Product product = new Product("사이다", new Inventory(1000L, BigInteger.valueOf(8), promotion));
        repository.save(product);
        assertThat(repository.count()).isEqualTo(1);
    }

    @DisplayName("Map에서 이름으로 상품을 찾는다")
    @Test
    void findByName() {
        BuyNGetOneFree promotion = BuyNGetOneFree.create("탄산2+1",
                BigInteger.TWO, BigInteger.ONE,
                LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"));
        Product product = new Product("사이다", new Inventory(1000L, BigInteger.valueOf(8), promotion));
        Product saved = repository.save(product);
        assertThat(repository.findByName("사이다")).isEqualTo(saved);
    }
}