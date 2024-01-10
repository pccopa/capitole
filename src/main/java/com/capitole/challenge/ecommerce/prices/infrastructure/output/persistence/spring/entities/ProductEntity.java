package com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence.spring.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "PRODUCT")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @OneToMany (mappedBy = "product")
    private List<PriceEntity> prices;
    @ManyToOne
    private BrandEntity brand;

}
