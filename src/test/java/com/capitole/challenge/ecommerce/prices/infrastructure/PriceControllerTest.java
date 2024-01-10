package com.capitole.challenge.ecommerce.prices.infrastructure;


import com.capitole.challenge.ecommerce.prices.application.ports.out.PriceByDateResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void findGeneralPrice() {
        // Datos de prueba
        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime searchDate = LocalDateTime.of(2020, 06, 14, 10,00);

        String url = String.format("http://localhost:%d/v1/prices?brandId=%d&productId=%d&searchDate=%s",
                port, brandId, productId, searchDate);

        PriceByDateResponse response = restTemplate.getForObject(url, PriceByDateResponse.class);

        PriceByDateResponse expected = new PriceByDateResponse();
        expected.setPrice(35.50);
        expected.setPriceList(1L);
        expected.setProductId(35455L);
        expected.setBrandId(1L);
        expected.setRequestDate(searchDate);

        assertEquals(expected, response);
    }

    @Test
    public void findPriorityPriceWithOverlappedDate() {
        // Datos de prueba
        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime searchDate = LocalDateTime.of(2020, 06, 14, 16,00);

        String url = String.format("http://localhost:%d/v1/prices?brandId=%d&productId=%d&searchDate=%s",
                port, brandId, productId, searchDate);

        PriceByDateResponse response = restTemplate.getForObject(url, PriceByDateResponse.class);

        PriceByDateResponse expected = new PriceByDateResponse();
        expected.setPrice(25.45);
        expected.setPriceList(2L);
        expected.setProductId(35455L);
        expected.setBrandId(1L);
        expected.setRequestDate(searchDate);

        assertEquals(expected, response);
    }

}
