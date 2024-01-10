package com.capitole.challenge.ecommerce.prices.infrastructure;

import com.capitole.challenge.ecommerce.prices.application.ports.out.PriceByDateResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class PriceSteps {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    private Long brandId;
    private Long productId;
    private String searchDate;
    private PriceByDateResponse searchResult;

    @Given("the brandId is {long}")
    public void theBrandIdIsBrandId(long brandId) {
        this.brandId=brandId;
    }

    @Given("the productId is {long}")
    public void givenProductId(Long productId) {
        this.productId = productId;
    }

    @Given("the searchDate is {string}")
    public void givenSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

    @When("I search for the priority price for selected date")
    public void whenSearchForPrice() {
        String url = String.format("http://localhost:%d/v1/prices?brandId=%d&productId=%d&searchDate=%s",
                port, brandId, productId, searchDate);
        this.searchResult = restTemplate.getForObject(url, PriceByDateResponse.class);
    }

    @Then("the response should have price {double}")
    public void thenResponseShouldHavePrice(Double expectedPrice) {
        assertEquals(expectedPrice, searchResult.getPrice());
    }

    @Then("the response should have priceList {long}")
    public void thenResponseShouldHavePriceList(Long expectedPriceList) {
        assertEquals(expectedPriceList, searchResult.getPriceList());
    }

    @Then("the response should have productId {long}")
    public void thenResponseShouldHaveProductId(Long expectedProductId) {
        assertEquals(expectedProductId, searchResult.getProductId());
    }

    @Then("the response should have brandId {long}")
    public void thenResponseShouldHaveBrandId(Long expectedBrandId) {
        assertEquals(expectedBrandId, searchResult.getBrandId());
    }


}
