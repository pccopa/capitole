package com.capitole.challenge.ecommerce.prices.domain.model;

import com.capitole.challenge.ecommerce.prices.domain.exceptions.InvalidCurrencyException;
import lombok.Data;

@Data
public class Currency {

    private CurrencyDetail detail;

    public Currency (String currencyName) {
        if (!isValidISOCurrency(currencyName)) {
            throw new InvalidCurrencyException("An invalid ISO currency code has been provided");
        }
        detail = new CurrencyDetail(currencyName);
    }


    public boolean isValidISOCurrency (String currencyName) {
        return java.util.Currency.getAvailableCurrencies()
                .stream()
                .anyMatch(currency -> currency.getCurrencyCode().equals(currencyName));
    }
}
