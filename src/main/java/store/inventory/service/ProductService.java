package store.inventory.service;

import store.domain.Promotion;
import store.inventory.file.dto.PromotionSaveDto;
import store.repository.PromotionRepository;

import java.math.BigInteger;
import java.util.List;

public class ProductService {

    private final PromotionRepository promotionRepository;

    public ProductService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public BigInteger savePromotions(List<PromotionSaveDto> dtos) {
        List<Promotion> promotions = dtos.stream().map(PromotionSaveDto::of).toList();
        promotions.forEach(promotionRepository::save);
        return BigInteger.valueOf(promotions.size());
    }
}
