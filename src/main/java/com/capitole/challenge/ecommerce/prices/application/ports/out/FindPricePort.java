package com.capitole.challenge.ecommerce.prices.application.ports.out;

import com.capitole.challenge.ecommerce.prices.domain.model.Price;

import java.time.LocalDateTime;

public interface FindPricePort {

    Price findPriceWithPriority (Long brandId, Long productId, LocalDateTime dateSearch);

}
