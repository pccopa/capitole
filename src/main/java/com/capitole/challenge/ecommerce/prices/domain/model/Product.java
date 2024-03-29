package com.capitole.challenge.ecommerce.prices.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private ProductDetail detail;
    private List<Price> prices;
    private Brand brand;


    public Optional<Price> findPriorityPrice(LocalDateTime searchDate) {
        return prices.stream()
                .sorted(Comparator.comparingInt(Price::getPriority).reversed())
                .filter(price -> price.isBetween(searchDate))
                .findFirst();
    }



}
