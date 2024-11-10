package store.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Product;

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
        Product product = new Product("사이다");
        repository.save(product);
        assertThat(repository.count()).isEqualTo(1);
    }

    @DisplayName("Map에서 이름으로 상품을 찾는다")
    @Test
    void findByName() {
        Product product = new Product("사이다");
        Product saved = repository.save(product);
        assertThat(repository.findByName("사이다").get()).isEqualTo(saved);
    }
}
