package store.inventory.service;

import store.domain.*;
import store.inventory.file.dto.ProductSaveDto;
import store.inventory.file.dto.PromotionSaveDto;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static store.sale.common.SaleExceptionCode.PROMOTION_NOT_FOUND;

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
        System.out.println("저장된 상품 개수: "+ productRepository.count());
        return productRepository.count();
    }
}
