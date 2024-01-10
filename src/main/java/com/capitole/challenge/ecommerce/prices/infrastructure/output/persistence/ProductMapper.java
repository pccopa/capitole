package com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence;

import com.capitole.challenge.ecommerce.prices.domain.model.Price;
import com.capitole.challenge.ecommerce.prices.domain.model.Product;
import com.capitole.challenge.ecommerce.prices.domain.model.ProductDetail;
import com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence.spring.entities.PriceEntity;
import com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence.spring.entities.ProductEntity;

import java.util.ArrayList;

public class ProductMapper {

    public static ProductEntity domainToEntity (Product product) {
        ProductEntity entity = new ProductEntity();
        entity.setId(product.getDetail().getId());
        entity.setDescription(product.getDetail().getName());

        entity.setPrices(new ArrayList<>());
        for (Price price: product.getPrices()) {
            entity.getPrices().add (PriceMapper.domainToEntity(price));
        }

        entity.setBrand(BrandMapper.domainToEntity (product.getBrand()));

        return entity;
    }

    public static Product entityToDomain (ProductEntity entity) {
        Product product = new Product();
        product.setDetail(new ProductDetail(entity.getId(), entity.getDescription()));

        product.setPrices(new ArrayList<>());
        for (PriceEntity priceEntity: entity.getPrices()) {
            product.getPrices().add (PriceMapper.entityToDomain(priceEntity));
        }

        product.setBrand(BrandMapper.entityToDomain (entity.getBrand()));
        return product;
    }

}
