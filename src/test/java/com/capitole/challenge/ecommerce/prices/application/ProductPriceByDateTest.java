package com.capitole.challenge.ecommerce.prices.application;

import com.capitole.challenge.ecommerce.prices.application.ports.in.FindPriceByDateCommand;
import com.capitole.challenge.ecommerce.prices.application.ports.out.FindProductPort;
import com.capitole.challenge.ecommerce.prices.application.ports.out.PriceByDateResponse;
import com.capitole.challenge.ecommerce.prices.application.service.ProductPriceByDate;
import com.capitole.challenge.ecommerce.prices.domain.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class ProductPriceByDateTest {

    @InjectMocks
    private ProductPriceByDate service;
    @Mock
    private FindProductPort productPort;

    @Test
    public void findPriceWithOverlappingDate () {
        LocalDateTime now = LocalDateTime.now();
        FindPriceByDateCommand command = new FindPriceByDateCommand(1L,1L, now);
        Product productMock = buildProductMock();
        when(productPort.findProduct(anyLong(), anyLong())).thenReturn(productMock);

        var response = service.find(command);
        PriceByDateResponse expected = new PriceByDateResponse();
        expected.setPrice(40d);
        expected.setPriceList(4L);
        expected.setProductId(1l);
        expected.setBrandId(1L);
        expected.setRequestDate(now);

        assertEquals(expected, response);

    }
    @Test
    public void findPriceWithoutOverlappingDate () {
        LocalDateTime fiveDaysAgo = LocalDateTime.now().minusDays(5);
        FindPriceByDateCommand command = new FindPriceByDateCommand(1L,1L, fiveDaysAgo);
        Product productMock = buildProductMock();
        when(productPort.findProduct(anyLong(), anyLong())).thenReturn(productMock);

        var response = service.find(command);
        PriceByDateResponse expected = new PriceByDateResponse();
        expected.setPrice(20d);
        expected.setPriceList(2L);
        expected.setProductId(1l);
        expected.setBrandId(1L);
        expected.setRequestDate(fiveDaysAgo);

        assertEquals(expected, response);

    }



    private Product buildProductMock() {
        Brand b = new Brand();
        b.setName("ZARA");
        b.setId(1L);

        Product p = new Product();
        p.setBrand(new Brand());
        p.setPrices(buildMockPrices());
        p.setDetail(new ProductDetail(1L, "ZAPATOS"));

        return p;
    }


    /**
     * "Create a price list with overlapping date ranges."
     * @return
     */
    private List<Price> buildMockPrices() {
        Price p1 = new Price();
        p1.setPrice(10d);
        p1.setStartDate(LocalDateTime.now().minusDays(1));
        p1.setEndDate(LocalDateTime.now().plusDays(1));
        p1.setCurrency(Currency.create("EUR"));
        p1.setPriceList(1L);
        p1.setPriority(0);

        Price p2 = new Price();
        p2.setPrice(20d);
        p2.setStartDate(LocalDateTime.now().minusDays(6));
        p2.setEndDate(LocalDateTime.now().minusDays(4));
        p2.setCurrency(Currency.create("EUR"));
        p2.setPriceList(2L);
        p2.setPriority(2);

        Price p3 = new Price();
        p3.setPrice(30d);
        p3.setStartDate(LocalDateTime.now().plusDays(1));
        p3.setEndDate(LocalDateTime.now().plusDays(2));
        p3.setCurrency(Currency.create("EUR"));
        p3.setPriceList(3L);
        p3.setPriority(3);

        Price p4 = new Price();
        p4.setPrice(40d);
        p4.setStartDate(LocalDateTime.now().minusDays(1));
        p4.setEndDate(LocalDateTime.now().plusDays(1));
        p4.setCurrency(Currency.create("EUR"));
        p4.setPriceList(4L);
        p4.setPriority(4);

        return List.of(p1, p2, p3, p4);
    }




}
