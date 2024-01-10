Feature: Search priority prices

  Scenario Outline: Find price with correct data
    Given the brandId is <BrandId>
    And the productId is <ProductId>
    And the searchDate is <SearchDate>
    When I search for the priority price for selected date
    Then the response should have price <ExpectedPrice>
    And the response should have priceList <ExpectedPriceList>
    And the response should have productId <ExpectedProductId>
    And the response should have brandId <ExpectedBrandId>

    Examples:
      | BrandId   | ProductId | SearchDate           | ExpectedPrice | ExpectedPriceList | ExpectedProductId | ExpectedBrandId |
      |1         | 35455    | '2020-06-14T10:00:00' | 35.50         | 1                 | 35455            | 1                |
      |1         | 35455    | '2020-06-14T16:00:00' | 25.45         | 2                 | 35455            | 1                |
      |1         | 35455    | '2020-06-14T21:00:00' | 35.50         | 1                 | 35455            | 1                |
      |1         | 35455    | '2020-06-15T10:00:00' | 30.50         | 3                 | 35455            | 1                |
      |1         | 35455    | '2020-06-16T21:00:00' | 38.95         | 4                 | 35455            | 1                |
