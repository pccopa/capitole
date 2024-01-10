package com.capitole.challenge.ecommerce.prices.domain;

import com.capitole.challenge.ecommerce.prices.domain.exceptions.InvalidCurrencyException;
import com.capitole.challenge.ecommerce.prices.domain.model.Currency;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CurrencyTest {

    @Test
    public void validCurrencyName () {
        assertDoesNotThrow(()-> {
            Currency.create("EUR");
        });
    }

    @Test
    public void inValidCurrencyName () {
        assertThrows(InvalidCurrencyException.class, ()-> {
            Currency.create("asdf123");
        });
    }

}
