package com.capitole.challenge.ecommerce.prices.application.service;

import com.capitole.challenge.ecommerce.prices.application.ports.in.FindPriceByDateCommand;
import com.capitole.challenge.ecommerce.prices.application.ports.in.FindProductPriceByDatePort;
import com.capitole.challenge.ecommerce.prices.application.ports.out.PriceByDateResponse;
import com.capitole.challenge.ecommerce.shared.UseCase;

@UseCase
public class ProductPriceByDate implements FindProductPriceByDatePort {
    @Override
    public PriceByDateResponse find(FindPriceByDateCommand command) {
        return null;
    }
}
