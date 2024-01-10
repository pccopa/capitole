package com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence;

import com.capitole.challenge.ecommerce.prices.application.ports.out.FindProductPort;
import com.capitole.challenge.ecommerce.prices.domain.model.Product;
import com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence.spring.repositories.ProductRepository;
import com.capitole.challenge.ecommerce.shared.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class FindProductAdapter implements FindProductPort {

    private final ProductRepository productRepository;

    @Override
    public Product findProduct(Long productId, Long brandId) {
        return productRepository.findByIdAndBrandId(productId, brandId)
                .map(ProductMapper::entityToDomain)
                .orElseThrow();
    }
}
