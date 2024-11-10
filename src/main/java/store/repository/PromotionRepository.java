package store.repository;

import store.domain.Promotion;

import java.math.BigInteger;
import java.util.Optional;

public interface PromotionRepository {

    Promotion save(Promotion promotion);
    Optional<Promotion> findByName(String name);
    BigInteger count();
}
