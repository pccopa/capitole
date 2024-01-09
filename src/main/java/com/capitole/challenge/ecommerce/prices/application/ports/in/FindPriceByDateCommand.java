package com.capitole.challenge.ecommerce.prices.application.ports.in;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class FindPriceByDateCommand {
    private Long brandId;
    private Long productId;
    private LocalDateTime searchDate;

}
