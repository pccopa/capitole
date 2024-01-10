package com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence.spring.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table (name = "BRAND")
public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany (fetch = FetchType.LAZY, mappedBy = "brand")
    private List<ProductEntity> products;
}
