package com.capitole.challenge.ecommerce.prices.application.ports.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public record PriceByDateResponse (
    Long productId,
    Long brandId,
    Long priceList,
    LocalDateTime requestDate,
    Double price
)
{
}
