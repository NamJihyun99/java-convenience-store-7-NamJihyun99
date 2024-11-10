package store.repository;

import store.domain.Promotion;

import java.math.BigInteger;

public interface PromotionRepository {

    Promotion save(Promotion promotion);
    Promotion findByName(String name);
    BigInteger count();
}
