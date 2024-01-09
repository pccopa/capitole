package com.capitole.challenge.ecommerce.prices.application.ports.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceByDateResponse {

    private Long productId;
    private Long brandId;
    private Long priceList;
    private LocalDateTime requestDate;
    private Double price;


}
