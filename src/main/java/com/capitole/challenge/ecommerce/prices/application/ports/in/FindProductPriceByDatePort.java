package com.capitole.challenge.ecommerce.prices.application.ports.in;

import com.capitole.challenge.ecommerce.prices.application.ports.out.PriceByDateResponse;

public interface FindProductPriceByDatePort {

    PriceByDateResponse find (FindPriceByDateCommand command);
}
