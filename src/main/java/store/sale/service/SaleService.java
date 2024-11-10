package store.sale.service;

import store.domain.Product;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

import java.util.List;

public class SaleService {

    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    public SaleService(ProductRepository productRepository, PromotionRepository promotionRepository) {
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
    }

    public List<Product> readProducts() {
        return productRepository.findAll();
    }
}
