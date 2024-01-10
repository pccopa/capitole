package com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence.spring.repositories;

import com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence.spring.entities.PriceEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepositoryCustom {
    Optional<PriceEntity> findPriceByDate (Long brandId, Long productId, LocalDateTime applicationDate);

}
