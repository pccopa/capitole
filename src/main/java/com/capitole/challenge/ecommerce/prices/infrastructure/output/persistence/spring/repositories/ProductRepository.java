package com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence.spring.repositories;

import com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence.spring.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByIdAndBrandId(Long productId, Long brandId);

}
