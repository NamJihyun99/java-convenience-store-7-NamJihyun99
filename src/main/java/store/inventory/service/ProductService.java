package store.inventory.service;

import store.domain.*;
import store.inventory.file.dto.ProductSaveDto;
import store.inventory.file.dto.PromotionSaveDto;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

import java.math.BigInteger;
import java.util.List;

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
            Product product = ProductSaveDto.createProduct(productRepository.findByName(dto.name), dto, promotionRepository.findByName(dto.promotion));
            productRepository.save(product);
        }
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            List<Inventory> inventories = product.inventories();
            if (inventories.size() == 1 && (inventories.getFirst().promotion() instanceof BuyNGetOneFree)) {
                product.addInventory(new Inventory(inventories.getFirst().price(), BigInteger.ZERO, NonePromotion.getInstance()));
            }
        }

        return productRepository.count();
    }
}
