package com.capitole.challenge.ecommerce.prices.infrastructure.input;

import com.capitole.challenge.ecommerce.prices.application.ports.in.FindPriceByDateCommand;
import com.capitole.challenge.ecommerce.prices.application.ports.in.FindProductPriceByDatePort;
import com.capitole.challenge.ecommerce.prices.application.ports.out.PriceByDateResponse;
import com.capitole.challenge.ecommerce.shared.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@WebAdapter
@RequestMapping ("v1/prices")
@RequiredArgsConstructor
public class PriceController {

    private final FindProductPriceByDatePort findProductPriceByDatePort;

    @GetMapping
    public PriceByDateResponse getPricesByDate (@RequestParam ("brandId") Long brandId,
                                                @RequestParam ("productId") Long productId,
                                                @RequestParam ("searchDate") LocalDateTime searchDate) {
        FindPriceByDateCommand command = new FindPriceByDateCommand(brandId, productId, searchDate);
        return findProductPriceByDatePort.find(command);
    }

}
