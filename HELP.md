## Ecommerce price search

Project has only 1 controller, you can access it at http://localhost:8080/v1/prices

### Request Params

Endpoint has 3 query params to send a successfully request.


* brandId: Should be an integer number
* productId: Should be an integer number
* searchDate: Must be an string with ISO format "yyyy-MM-dd'T'HH:mm:ss'Z'"

Incorrect format will trigger an handled exception with a 400 BadRequest

### Searching available dates

After sending a correct request, service will be response in specific format.

This example show a correct request with founded price

```curl
curl --location 'localhost:8080/v1/prices?productId=35455&brandId=1&searchDate=2020-06-16T21%3A00%3A00Z'
```

We can see the request params was configured to search 
- productId=35455
- brandId=1 
- requestDate "2020-06-16T21:00:00"

Service after searching process, will give us this response. 
```json
{
    "productId": 35455,
    "brandId": 1,
    "priceList": 4,
    "requestDate": "2020-06-16T21:00:00",
    "price": 38.95
}
```

### Searching on unavailable dates
After sending a correct request, service will be response in specific format.
This example show a correct request with not founded price, product or brand.

```curl
curl --location 'localhost:8080/v1/prices?productId=23&brandId=34&searchDate=2020-06-16T21%3A00%3A00Z'
```
Service after searching process, will give us this response, this is a standard response for every case with error

```json
{
    "code": "NOT_FOUND",
    "message": "No value present",
    "url": "/v1/prices",
    "status": 404,
    "timestamp": "2024-01-10T15:43:56.984111302"
}
```
So, frontend should be handle errors to show valuable information to user, and avoiding extra logic on their service layer.


### Extra information.

At this moment, there are two different ways to work and get result.
On application layer, class ProductPriceByDate if you prefer, you can work directly with a query to resolve the challenge purpose, gaining performance to the detriment of code cleanliness and architecture. 

For this kind of complexity, I believe that hexagonal architecture is over-engineering.

### Further releases

- Maybe should be considered the use of Mapstruct to avoid manual mapper
- Includes Jacoco to report correctly code coverage
- App must be dockerized
- Consider every single property like a Value-Object
- Incorporate a few extra layers of abstraction to gain better decouple 
- Maybe adds an event sourcing pattern 
