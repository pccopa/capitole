package com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence.spring.entities;

import com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence.spring.validator.currency.ValidCurrency;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "PRICE")
public class PriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long priceList;
    @ManyToOne
    private ProductEntity product;
    private Integer priority;
    private Double price;
    @ValidCurrency
    private String currency;

}
