package store.repository;

import store.domain.Promotion;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryPromotionRepository implements PromotionRepository {

    private static final Map<String, Promotion> promotions = new ConcurrentHashMap<>();

    private MemoryPromotionRepository() {}

    public static MemoryPromotionRepository getInstance() {
        return new MemoryPromotionRepository();
    }

    @Override
    public Promotion save(Promotion promotion) {
        promotions.put(promotion.name(), promotion);
        return promotion;
    }

    @Override
    public Optional<Promotion> findByName(String name) {
        return Optional.ofNullable(promotions.get(name));
    }

    @Override
    public BigInteger count() {
        return BigInteger.valueOf(promotions.size());
    }

    void clear() {
        promotions.clear();
    }
}
