package store.inventory.service;

import store.domain.Product;
import store.domain.Promotion;
import store.inventory.dto.dto.ProductSaveDto;
import store.inventory.dto.dto.PromotionSaveDto;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public class ProductService {

    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;

    public ProductService(PromotionRepository promotionRepository, ProductRepository productRepository) {
        this.promotionRepository = promotionRepository;
        this.productRepository = productRepository;
    }

    public BigInteger savePromotions(List<PromotionSaveDto> dtos) {
        List<Promotion> promotions = dtos.stream()
                .map(PromotionSaveDto::of)
                .map(promotionRepository::save)
                .toList();
        return BigInteger.valueOf(promotions.size());
    }

    public BigInteger saveProducts(List<ProductSaveDto> dtos) {
        for (ProductSaveDto dto : dtos) {
            Optional<Promotion> promotion = promotionRepository.findByName(dto.promotion);
            Product product = ProductSaveDto.createProduct(productRepository.findByName(dto.name), dto, promotion);
            productRepository.save(product);
        }
        return productRepository.count();
    }
}
