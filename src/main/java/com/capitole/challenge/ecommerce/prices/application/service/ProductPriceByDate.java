package com.capitole.challenge.ecommerce.prices.application.service;

import com.capitole.challenge.ecommerce.prices.application.ports.in.FindPriceByDateCommand;
import com.capitole.challenge.ecommerce.prices.application.ports.in.FindProductPriceByDatePort;
import com.capitole.challenge.ecommerce.prices.application.ports.out.FindPricePort;
import com.capitole.challenge.ecommerce.prices.application.ports.out.PriceByDateResponse;
import com.capitole.challenge.ecommerce.prices.domain.model.Price;
import com.capitole.challenge.ecommerce.shared.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ProductPriceByDate implements FindProductPriceByDatePort {

    private final FindPricePort pricePort;

    @Override
    public PriceByDateResponse find(FindPriceByDateCommand command) {
        Price price = pricePort.findPriceWithPriority(command.getBrandId(), command.getProductId(), command.getSearchDate());
        return new PriceByDateResponse(command.getProductId(),
                command.getBrandId(),
                price.getPriceList(),
                command.getSearchDate(),
                price.getPrice());
    }
}
