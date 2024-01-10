package com.capitole.challenge.ecommerce.prices.application.ports.out;

import com.capitole.challenge.ecommerce.prices.domain.model.Product;

public interface FindProductPort {

    Product findProduct (Long productId, Long brandId);

}
