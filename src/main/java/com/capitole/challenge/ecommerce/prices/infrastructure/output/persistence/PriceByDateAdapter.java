package com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence;

import com.capitole.challenge.ecommerce.prices.application.ports.out.FindPricePort;
import com.capitole.challenge.ecommerce.prices.domain.model.Price;
import com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence.spring.repositories.PriceRepository;
import com.capitole.challenge.ecommerce.shared.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@PersistenceAdapter
@RequiredArgsConstructor
public class PriceByDateAdapter implements FindPricePort {

    private final PriceRepository priceRepository;

    @Override
    public Price findPriceWithPriority(Long brandId, Long productId, LocalDateTime dateSearch) {
        return priceRepository
                .findPriceByDate(brandId, productId, dateSearch)
                .map(PriceMapper::entityToDomain)
                .orElseThrow();
    }
}
