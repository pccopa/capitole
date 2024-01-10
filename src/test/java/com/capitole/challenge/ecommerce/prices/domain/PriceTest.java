package com.capitole.challenge.ecommerce.prices.domain;

import com.capitole.challenge.ecommerce.prices.domain.model.Currency;
import com.capitole.challenge.ecommerce.prices.domain.model.Price;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class PriceTest {

    @Test
    public void dateBetweenPriceDates () {
        Price price = new Price();
        var fiveDaysAgo = LocalDateTime.now().minusDays(5);
        var tomorrow = LocalDateTime.now().plusDays(1);
        var searchDate = LocalDateTime.now();
        price.setEndDate(tomorrow);
        price.setStartDate(fiveDaysAgo);

        assertTrue(price.isBetween(searchDate));
    }


    @Test
    public void dateBeforePriceDates () {
        Price price = new Price();
        var inFiveDays = LocalDateTime.now().plusDays(5);
        var tomorrow = LocalDateTime.now().plusDays(1);
        var searchDate = LocalDateTime.now();
        price.setEndDate(tomorrow);
        price.setStartDate(inFiveDays);

        assertFalse(price.isBetween(searchDate));
    }

    @Test
    public void dateAfterPriceDates () {
        Price price = new Price();
        var fiveDaysAgo = LocalDateTime.now().minusDays(5);
        var tomorrow = LocalDateTime.now().minusDays(1);
        var searchDate = LocalDateTime.now();
        price.setEndDate(tomorrow);
        price.setStartDate(fiveDaysAgo);

        assertFalse(price.isBetween(searchDate));
    }


    @Test
    public void getCurrencyName () {
        Currency curr = Currency.create("EUR");
        Price price = new Price();
        price.setCurrency(curr);

        assertEquals("EUR", price.getCurrencyName());
    }


}
