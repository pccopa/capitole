package com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence;

import com.capitole.challenge.ecommerce.prices.domain.model.Brand;
import com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence.spring.entities.BrandEntity;

public class BrandMapper {

    public static BrandEntity domainToEntity (Brand brand) {
        BrandEntity entity = new BrandEntity();
        entity.setId(brand.getId());
        entity.setName(brand.getName());
        return entity;
    }

    public static Brand entityToDomain(BrandEntity entity) {
        Brand brand = new Brand();
        brand.setId(entity.getId());
        brand.setName(entity.getName());
        return brand;
    }
}
