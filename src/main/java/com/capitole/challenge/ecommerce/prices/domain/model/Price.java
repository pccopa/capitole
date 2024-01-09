package com.capitole.challenge.ecommerce.prices.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Price {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long priceList;
    private Integer priority;
    private Double price;
    private String currency;


    public boolean isBetween (LocalDateTime searchCriteria) {
        return startDate.isBefore(searchCriteria)
                && endDate.isAfter(searchCriteria);
    }




}
