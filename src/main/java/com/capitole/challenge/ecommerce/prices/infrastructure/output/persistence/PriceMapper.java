package com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence;

import com.capitole.challenge.ecommerce.prices.domain.model.Currency;
import com.capitole.challenge.ecommerce.prices.domain.model.Price;
import com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence.spring.entities.PriceEntity;

public class PriceMapper {

    public static Price entityToDomain (PriceEntity entity) {
        Price price = new Price();
        price.setPrice(entity.getPrice());
        price.setPriceList(entity.getPriceList());
        price.setCurrency(Currency.create(entity.getCurrency()));
        price.setPriority(entity.getPriority());
        price.setStartDate(entity.getStartDate());
        price.setEndDate(entity.getEndDate());
        return price;
    }

    public static PriceEntity domainToEntity (Price domain) {
        PriceEntity entity = new PriceEntity();
        entity.setCurrency(domain.getCurrencyName());
        entity.setPrice(domain.getPrice());
        entity.setPriority(domain.getPriority());
        entity.setEndDate(domain.getEndDate());
        entity.setStartDate(domain.getStartDate());
        entity.setPriceList(domain.getPriceList());
        return entity;
    }


}
