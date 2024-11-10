package store.repository;

import store.domain.NonePromotion;
import store.domain.Promotion;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryPromotionRepository implements PromotionRepository {

    private static final Map<String, Promotion> promotions = new ConcurrentHashMap<>();

    private MemoryPromotionRepository() {
        if (promotions.isEmpty()) {
            promotions.put("null", NonePromotion.getInstance());
        }
    }

    public static MemoryPromotionRepository getInstance() {
        return new MemoryPromotionRepository();
    }

    @Override
    public Promotion save(Promotion promotion) {
        promotions.put(promotion.name(), promotion);
        return promotion;
    }

    @Override
    public Promotion findByName(String name) {
        return promotions.get(name);
    }

    @Override
    public BigInteger count() {
        return BigInteger.valueOf(promotions.size());
    }

    void clear() {
        promotions.clear();
    }
}
