# eWallet
eWallet provides a REST API that allows you to register a credit card for an existing and valid buyer, 
access the limits of an already created credit card, buy an existing and valid cart, 
access a buyer's credit cards and pay a credit card.

- [REQUIREMENT 6](https://docs.google.com/document/d/1-nVFp7ijRl6yHUWIos8oNRI4qhW0ZN90/edit?usp=sharing&ouid=106995492824829831096&rtpof=true&sd=true)

### - Summary:
- [Classes](https://github.com/MarinaRuana/projeto-integrador-requisito-individual/tree/develop#--classes:~:text=%2D-,Classes,-%3A)
- [Enhanced Entity-Relationship](https://github.com/MarinaRuana/projeto-integrador-requisito-individual/tree/develop#--classes:~:text=%2D%20Enhanced%20Entity%2DRelationship%20(EER)%3A)
- [Methods](https://github.com/MarinaRuana/projeto-integrador-requisito-individual/tree/develop#--classes:~:text=%2D-,Methods,-%3A)
- [Postman Collection](https://github.com/MarinaRuana/projeto-integrador-requisito-individual/tree/develop#--classes:~:text=%2D-,Postman%20Collection%3A,-eWallet%20%2D%20Postman%20Collection)
- [Swagger](https://github.com/MarinaRuana/projeto-integrador-requisito-individual#:~:text=eWallet%20%2D%20Postman%20Collection-,%2D%20Swagger%3A,-%7B%22openapi%22%3A%223.0.1%22%2C%22info)
- [Unit test](https://github.com/MarinaRuana/projeto-integrador-requisito-individual/tree/develop#--classes:~:text=%2D-,Unit%20test,-%3A)
- [PayLoads](https://github.com/MarinaRuana/projeto-integrador-requisito-individual/tree/develop#--classes:~:text=%2D-,PayLoads,-%3A)

### - Classes:

 - [Buyer](https://github.com/MarinaRuana/projeto-integrador-requisito-individual/blob/develop/src/main/java/com/desafiofinal/praticafinal/model/Buyer.java)
 - [Cart](https://github.com/MarinaRuana/projeto-integrador-requisito-individual/blob/develop/src/main/java/com/desafiofinal/praticafinal/model/Cart.java)
 - [CreditCard](https://github.com/MarinaRuana/projeto-integrador-requisito-individual/blob/develop/src/main/java/com/desafiofinal/praticafinal/model/CreditCard.java)
 - [Payment](https://github.com/MarinaRuana/projeto-integrador-requisito-individual/blob/develop/src/main/java/com/desafiofinal/praticafinal/model/Payment.java)

### - Enhanced Entity-Relationship (EER):

![Class Diagram](https://cdn.discordapp.com/attachments/927534793892171798/1009154632964128869/tabela-pi-requisito-ndividual.png)

### - Methods:

#### CreditCard Routes - [CreditCardController](https://github.com/MarinaRuana/projeto-integrador-requisito-individual/blob/develop/src/main/java/com/desafiofinal/praticafinal/controller/CreditCardController.java)
- 📪 Post: registerCard()
- ⬅️ Get: getCreditCardLimits()
- ⬅️ Get: getCreditCardBill()
- ⬅️ Get: getWallet()
- ➡️ Put: buyCart()
- ➡️ Put: updateStatus()


|  HTTP  | URL MODEL                                | DESCRIPTION                                                                                                                                     |    US-CODE     |
|:------:|:-----------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------|:--------------:|
| `POST` | /api/v1/credit-card/register_card        | registers a new credit card for an existing buyer user in the database                                                                          | ml-e-wallet-06 |
| `GET`  | /api/v1/credit-card/limits/{cardId}      | return a String with limitTotal and limitAvailable from a credit card                                                                           | ml-e-wallet-06 |
| `GET`  | /api/v1/credit-card/bill/{cardNumber}    | returns a collection of carts that were purchased by this credit card                                                                           | ml-e-wallet-06 |
| `GET`  | /api/v1/credit-card/wallet/{buyerId}     | returns a valid buyer's card wallet                                                                                                             | ml-e-wallet-06 |
| `PUT`  | /buy_cart/{cartId}/{cardNumber}          | buy an existing cart open, passing its status to finished and debiting the total value of this cart from the available limit of the credit card | ml-e-wallet-06 |
| `PUT`  | /update_status/{cardNumber}              | changes the status of the credit card from the card number                                                                                      | ml-e-wallet-06 |

#### Payment Routes - [PaymentController](https://github.com/MarinaRuana/projeto-integrador-requisito-individual/blob/develop/src/main/java/com/desafiofinal/praticafinal/controller/PaymentController.java)
- 📪 Post: payCreditCard()


|  HTTP  | URL MODEL                                | DESCRIPTION                                                                     |    US-CODE     |
|:------:|:-----------------------------------------|:--------------------------------------------------------------------------------|:--------------:|
| `POST` | /api/v1/payment/credit-card/{cartNumber} | create a payment for a credit card when payer are already the credit card owner | ml-e-wallet-06 |

### - Postman Collection: [eWallet - Postman Collection](https://www.getpostman.com/collections/9155130994dd915b5cb1)
*the postman collection json file can be found in the diretory addicionalDocs*

### - [Swagger:](http://localhost:8080/swagger-ui/index.html)
```
{"openapi":"3.0.1","info":{"title":"OpenAPI definition","version":"v0"},"servers":[{"url":"http://localhost:8080","description":"Generated server url"}],"paths":{"/api/v1/fresh-products/purchases/update/{purchaseId}":{"put":{"tags":["cart-controller"],"operationId":"updateStatus","parameters":[{"name":"purchaseId","in":"path","required":true,"schema":{"type":"integer","format":"int64"}}],"responses":{"200":{"description":"OK","content":{"*/*":{"schema":{"type":"string"}}}}}}},"/api/v1/fresh-products/inboundorder/update":{"put":{"tags":["in-bound-order-controller"],"operationId":"updateInBoundOrder","requestBody":{"content":{"application/json":{"schema":{"$ref":"#/components/schemas/InBoundOrderRequestDTO"}}},"required":true},"responses":{"200":{"description":"OK","content":{"*/*":{"schema":{"$ref":"#/components/schemas/InBoundOrderResponseDTO"}}}}}}},"/api/v1/credit-card/update_status/{cardNumber}":{"put":{"tags":["credit-card-controller"],"operationId":"updateStatus_1","parameters":[{"name":"cardNumber","in":"path","required":true,"schema":{"type":"string"}}],"responses":{"200":{"description":"OK","content":{"*/*":{"schema":{"type":"string"}}}}}}},"/api/v1/credit-card/buy_cart/{cartId}/{cardNumber}":{"put":{"tags":["credit-card-controller"],"operationId":"buyCart","parameters":[{"name":"cartId","in":"path","required":true,"schema":{"type":"integer","format":"int64"}},{"name":"cardNumber","in":"path","required":true,"schema":{"type":"string"}}],"responses":{"200":{"description":"OK","content":{"*/*":{"schema":{"type":"string"}}}}}}},"/api/v1/payment/credit_card/{cardNumber}":{"post":{"tags":["payment-controller"],"operationId":"payCreditCard","parameters":[{"name":"cardNumber","in":"path","required":true,"schema":{"type":"string"}}],"requestBody":{"content":{"application/json":{"schema":{"$ref":"#/components/schemas/PaymentDTO"}}},"required":true},"responses":{"200":{"description":"OK","content":{"*/*":{"schema":{"type":"string"}}}}}}},"/api/v1/fresh-products/seller":{"post":{"tags":["seller-controller"],"operationId":"insertSeller","requestBody":{"content":{"application/json":{"schema":{"$ref":"#/components/schemas/SellerDTO"}}},"required":true},"responses":{"200":{"description":"OK","content":{"*/*":{"schema":{"$ref":"#/components/schemas/SellerDTO"}}}}}}},"/api/v1/fresh-products/purchases/insert":{"post":{"tags":["cart-controller"],"operationId":"createNewCart","requestBody":{"content":{"application/json":{"schema":{"$ref":"#/components/schemas/CartDto"}}},"required":true},"responses":{"200":{"description":"OK","content":{"*/*":{"schema":{"type":"number","format":"double"}}}}}}},"/api/v1/fresh-products/product":{"post":{"tags":["product-controller"],"operationId":"insertProduct","requestBody":{"content":{"application/json":{"schema":{"$ref":"#/components/schemas/ProductDTO"}}},"required":true},"responses":{"200":{"description":"OK","content":{"*/*":{"schema":{"$ref":"#/components/schemas/ProductDTO"}}}}}}},"/api/v1/fresh-products/inboundorder/insert":{"post":{"tags":["in-bound-order-controller"],"operationId":"create","requestBody":{"content":{"application/json":{"schema":{"$ref":"#/components/schemas/InBoundOrderRequestDTO"}}},"required":true},"responses":{"200":{"description":"OK","content":{"*/*":{"schema":{"$ref":"#/components/schemas/InBoundOrderResponseDTO"}}}}}}},"/api/v1/credit-card/register_card":{"post":{"tags":["credit-card-controller"],"operationId":"registerCard","requestBody":{"content":{"application/json":{"schema":{"$ref":"#/components/schemas/CreditCardDTO"}}},"required":true},"responses":{"200":{"description":"OK","content":{"*/*":{"schema":{"$ref":"#/components/schemas/CreditCardDTO"}}}}}}},"/api/v1/fresh-products/sectorProducts/{productId}":{"get":{"tags":["batch-stock-controller"],"operationId":"getBatchSector","parameters":[{"name":"productId","in":"path","required":true,"schema":{"type":"integer","format":"int64"}}],"responses":{"200":{"description":"OK","content":{"*/*":{"schema":{"type":"array","items":{"$ref":"#/components/schemas/ResponseSectorQuery"}}}}}}}},"/api/v1/fresh-products/sectorProducts/{productId}/{string}":{"get":{"tags":["batch-stock-controller"],"operationId":"getBatchSectorOrdered","parameters":[{"name":"productId","in":"path","required":true,"schema":{"type":"integer","format":"int64"}},{"name":"string","in":"path","required":true,"schema":{"type":"string"}}],"responses":{"200":{"description":"OK","content":{"*/*":{"schema":{"type":"array","items":{"$ref":"#/components/schemas/ResponseSectorQuery"}}}}}}}},"/api/v1/fresh-products/sectorProducts/stockByDays/{sectorId}/{days}":{"get":{"tags":["batch-stock-controller"],"operationId":"getStockByDueDate","parameters":[{"name":"sectorId","in":"path","required":true,"schema":{"type":"integer","format":"int64"}},{"name":"days","in":"path","required":true,"schema":{"type":"integer","format":"int64"}}],"responses":{"200":{"description":"OK","content":{"*/*":{"schema":{"$ref":"#/components/schemas/ResponseStock"}}}}}}},"/api/v1/fresh-products/sectorProducts/stockByCategoryDays/{category}/{days}":{"get":{"tags":["batch-stock-controller"],"operationId":"getCategoryDueDate","parameters":[{"name":"category","in":"path","required":true,"schema":{"type":"string"}},{"name":"days","in":"path","required":true,"schema":{"type":"integer","format":"int64"}}],"responses":{"200":{"description":"OK","content":{"*/*":{"schema":{"$ref":"#/components/schemas/ResponseStock"}}}}}}},"/api/v1/fresh-products/sectorProducts/sector/{productId}":{"get":{"tags":["batch-stock-controller"],"operationId":"getTotalQuantitySector","parameters":[{"name":"productId","in":"path","required":true,"schema":{"type":"integer","format":"int64"}}],"responses":{"200":{"description":"OK","content":{"*/*":{"schema":{"$ref":"#/components/schemas/ResponseSectorTotalQuantity"}}}}}}},"/api/v1/fresh-products/purchases/ListProducts/{purchaseId}":{"get":{"tags":["cart-controller"],"operationId":"getProducts","parameters":[{"name":"purchaseId","in":"path","required":true,"schema":{"type":"integer","format":"int64"}}],"responses":{"200":{"description":"OK","content":{"*/*":{"schema":{"type":"array","items":{"$ref":"#/components/schemas/BatchStockResponseDTO"}}}}}}}},"/api/v1/fresh-products/product/{category}":{"get":{"tags":["product-controller"],"operationId":"getProductBySector","parameters":[{"name":"category","in":"path","required":true,"schema":{"type":"string"}}],"responses":{"200":{"description":"OK","content":{"*/*":{"schema":{"type":"array","items":{"$ref":"#/components/schemas/BatchStockResponseDTO"}}}}}}}},"/api/v1/fresh-products/product/products":{"get":{"tags":["product-controller"],"operationId":"getAllProducts","responses":{"200":{"description":"OK","content":{"*/*":{"schema":{"type":"array","items":{"$ref":"#/components/schemas/ProductDTO"}}}}}}}},"/api/v1/credit-card/wallet/{buyerId}":{"get":{"tags":["credit-card-controller"],"operationId":"getWallet","parameters":[{"name":"buyerId","in":"path","required":true,"schema":{"type":"integer","format":"int64"}}],"responses":{"200":{"description":"OK","content":{"*/*":{"schema":{"type":"array","items":{"$ref":"#/components/schemas/CreditCardDTO"}}}}}}}},"/api/v1/credit-card/limits/{cardId}":{"get":{"tags":["credit-card-controller"],"operationId":"getCreditCardLimits","parameters":[{"name":"cardId","in":"path","required":true,"schema":{"type":"integer","format":"int64"}}],"responses":{"200":{"description":"OK","content":{"*/*":{"schema":{"type":"string"}}}}}}},"/api/v1/credit-card/bill/{cardNumber}":{"get":{"tags":["credit-card-controller"],"operationId":"getCreditCardBill","parameters":[{"name":"cardNumber","in":"path","required":true,"schema":{"type":"string"}}],"responses":{"200":{"description":"OK","content":{"*/*":{"schema":{"type":"array","items":{"$ref":"#/components/schemas/CartResponseDTO"}}}}}}}}},"components":{"schemas":{"BatchStock":{"type":"object","properties":{"batchId":{"type":"integer","format":"int64"},"currentTemperature":{"type":"number","format":"float"},"minimumTemperature":{"type":"number","format":"float"},"initialQuantity":{"type":"integer","format":"int64"},"currentQuantity":{"type":"integer","format":"int64"},"manufacturingDate":{"type":"string","format":"date"},"manufacturingTime":{"type":"string","format":"date"},"dueDate":{"type":"string","format":"date"},"inBoundOrder":{"$ref":"#/components/schemas/InBoundOrder"},"product":{"$ref":"#/components/schemas/Product"}}},"BatchStockDTO":{"required":["currentQuantity","currentTemperature","dueDate","initialQuantity","manufacturingDate","manufacturingTime","minimumTemperature","product"],"type":"object","properties":{"batchNumber":{"type":"integer","format":"int64"},"product":{"$ref":"#/components/schemas/ProductResponseDTO"},"currentTemperature":{"type":"number","format":"float"},"minimumTemperature":{"type":"number","format":"float"},"initialQuantity":{"type":"integer","format":"int64"},"currentQuantity":{"type":"integer","format":"int64"},"manufacturingDate":{"type":"string","format":"date"},"manufacturingTime":{"type":"string","format":"date"},"dueDate":{"type":"string","format":"date"},"inBoundOrderId":{"type":"integer","format":"int64"}}},"InBoundOrder":{"type":"object","properties":{"orderId":{"type":"integer","format":"int64"},"dateTime":{"type":"string","format":"date"},"batchStockList":{"type":"array","items":{"$ref":"#/components/schemas/BatchStock"}},"sector":{"$ref":"#/components/schemas/Sector"}}},"InBoundOrderDTO":{"type":"object","properties":{"batchStock":{"type":"array","items":{"$ref":"#/components/schemas/BatchStockDTO"}}}},"InBoundOrderRequestDTO":{"required":["batchStock","dateTime","sector"],"type":"object","properties":{"orderId":{"type":"integer","format":"int64"},"dateTime":{"type":"string","format":"date"},"sector":{"$ref":"#/components/schemas/SectorDTO"},"batchStock":{"type":"array","items":{"$ref":"#/components/schemas/BatchStockDTO"}}}},"Manager":{"type":"object","properties":{"managerId":{"type":"integer","format":"int64"},"managerName":{"type":"string"},"wareHouse":{"$ref":"#/components/schemas/WareHouse"}}},"Product":{"type":"object","properties":{"id":{"type":"integer","format":"int64"},"validateDate":{"type":"string","format":"date"},"price":{"type":"number","format":"double"},"productType":{"type":"string"},"productName":{"type":"string"},"seller":{"$ref":"#/components/schemas/Seller"},"bulk":{"type":"number","format":"double"}}},"ProductResponseDTO":{"type":"object","properties":{"id":{"type":"integer","format":"int64"}}},"Sector":{"type":"object","properties":{"sectorId":{"type":"integer","format":"int64"},"category":{"type":"string"},"capacity":{"type":"number","format":"double"},"orderList":{"type":"array","items":{"$ref":"#/components/schemas/InBoundOrder"}},"wareHouse":{"$ref":"#/components/schemas/WareHouse"},"maxCapacity":{"type":"number","format":"double"}}},"SectorDTO":{"type":"object","properties":{"sectorId":{"type":"integer","format":"int64"},"category":{"type":"string"},"capacity":{"type":"number","format":"double"},"orderList":{"type":"array","items":{"$ref":"#/components/schemas/InBoundOrderDTO"}},"wareHouse":{"$ref":"#/components/schemas/WareHouseDTO"}}},"Seller":{"type":"object","properties":{"id":{"type":"integer","format":"int64"},"sellerName":{"type":"string"}}},"WareHouse":{"type":"object","properties":{"wareHouseId":{"type":"integer","format":"int64"},"manager":{"$ref":"#/components/schemas/Manager"}}},"WareHouseDTO":{"type":"object","properties":{"wareHouseId":{"type":"integer","format":"int64"},"sectorList":{"type":"array","items":{"$ref":"#/components/schemas/Sector"}}}},"InBoundOrderResponseDTO":{"type":"object","properties":{"batchStock":{"type":"array","items":{"$ref":"#/components/schemas/BatchStockDTO"}}}},"BuyerDto":{"type":"object","properties":{"buyerId":{"type":"integer","format":"int64"},"buyerName":{"type":"string"}}},"PaymentDTO":{"required":["payer","value"],"type":"object","properties":{"id":{"type":"integer","format":"int64"},"payer":{"$ref":"#/components/schemas/BuyerDto"},"value":{"minimum":1,"exclusiveMinimum":false,"type":"number","format":"double"}}},"SellerDTO":{"type":"object","properties":{"idSeller":{"type":"integer","format":"int64"},"sellerName":{"type":"string"}}},"CartDto":{"required":["buyer","date","purchaseList"],"type":"object","properties":{"cartId":{"type":"integer","format":"int64"},"buyer":{"$ref":"#/components/schemas/BuyerDto"},"totalPrice":{"type":"number","format":"double"},"date":{"type":"string","format":"date"},"orderStatus":{"type":"string"},"purchaseList":{"type":"array","items":{"$ref":"#/components/schemas/PurchaseDTO"}}}},"PurchaseDTO":{"required":["batchStock","pricePerProduct","productQuantity"],"type":"object","properties":{"purchaseId":{"type":"integer","format":"int64"},"cart":{"$ref":"#/components/schemas/CartDto"},"batchStock":{"$ref":"#/components/schemas/BatchStockDTO"},"pricePerProduct":{"type":"number","format":"double"},"productQuantity":{"type":"integer","format":"int32"}}},"ProductDTO":{"type":"object","properties":{"id":{"type":"integer","format":"int64"},"productType":{"type":"string"},"validateDate":{"type":"string","format":"date"},"price":{"type":"number","format":"double"},"productName":{"type":"string"},"seller":{"$ref":"#/components/schemas/SellerDTO"},"bulk":{"type":"number","format":"double"}}},"CreditCardDTO":{"required":["cardNumber","cardOwner","limitAvailable","limitTotal","status"],"type":"object","properties":{"id":{"type":"integer","format":"int64"},"cardOwner":{"$ref":"#/components/schemas/BuyerDto"},"cardNumber":{"maxLength":6,"minLength":6,"pattern":"[0-9]*","type":"string"},"limitTotal":{"minimum":100,"exclusiveMinimum":false,"type":"number","format":"double"},"limitAvailable":{"minimum":1,"exclusiveMinimum":false,"type":"number","format":"double"},"status":{"type":"boolean"}}},"ResponseSectorQuery":{"type":"object","properties":{"sector":{"$ref":"#/components/schemas/SectorQuery"},"productId":{"type":"integer","format":"int64"},"stockList":{"type":"array","items":{"$ref":"#/components/schemas/StockQuery"}}}},"SectorQuery":{"type":"object","properties":{"sectorId":{"type":"integer","format":"int64"},"category":{"type":"string"}}},"StockQuery":{"type":"object","properties":{"batchId":{"type":"integer","format":"int64"},"currentQuantity":{"type":"integer","format":"int64"},"dueDate":{"type":"string","format":"date"}}},"ResponseStock":{"type":"object","properties":{"dataBaseStocks":{"type":"array","items":{"$ref":"#/components/schemas/ResponseStockQuery"}}}},"ResponseStockQuery":{"type":"object","properties":{"batchId":{"type":"integer","format":"int64"},"idProduct":{"type":"integer","format":"int64"},"productType":{"type":"string"},"dueDate":{"type":"string","format":"date"},"currentQuantity":{"type":"integer","format":"int64"}}},"ResponseSectorTotalQuantity":{"type":"object","properties":{"productId":{"type":"integer","format":"int64"},"sectorList":{"type":"array","items":{"$ref":"#/components/schemas/SectorQuantityQuery"}}}},"SectorQuantityQuery":{"type":"object","properties":{"sectorId":{"type":"integer","format":"int64"},"totalQuantity":{"type":"integer","format":"int64"}}},"BatchStockResponseDTO":{"type":"object","properties":{"batchId":{"type":"integer","format":"int64"},"currentQuantity":{"type":"integer","format":"int64"},"dueDate":{"type":"string","format":"date"},"productId":{"$ref":"#/components/schemas/ProductResponseDTO"}}},"CartResponseDTO":{"required":["buyer","date"],"type":"object","properties":{"cartId":{"type":"integer","format":"int64"},"buyer":{"$ref":"#/components/schemas/BuyerDto"},"totalPrice":{"type":"number","format":"double"},"date":{"type":"string","format":"date"},"orderStatus":{"type":"string"},"creditCard":{"type":"string"}}}}}}
```

**SQL query to populate the database with items needed for testing:**
```
INSERT INTO seller (id, seller_name) VALUES ('1', 'Marina');
INSERT INTO buyer (buyer_id, buyer_name) VALUES ('1', 'Marina');
INSERT INTO sector(sector_id, capacity, category, max_capacity) VALUES ('1', '10', 'FF', '1');
INSERT INTO product(id, bulk, price, product_name, id_seller, validate_date) VALUES ('1', '1', '1', '1', '1', '2022-09-01');
INSERT INTO product (id, bulk, price, product_name,id_seller, validate_date) VALUES ('2', '10', '2', '6','1','2022-07-30');
```

### - Unit test:
- [CreditCardImpServiceTest](https://github.com/MarinaRuana/projeto-integrador-requisito-individual/blob/develop/src/test/java/com/desafiofinal/praticafinal/service/CreditCardImpServiceTest.java) - 100% coverage
- [PaymentImpServiceTest](https://github.com/MarinaRuana/projeto-integrador-requisito-individual/blob/develop/src/test/java/com/desafiofinal/praticafinal/service/PaymentImpServiceTest.java) - 100% coverage

- total of service layer lines - 77%
### - PayLoads:

### **[POST] registerCard:** - */api/v1/credit-card/register_card*

### - Request:
- @RequestBody:
```
{  
    "cardOwner" : {
        "buyerId": 1
    },
    "cardNumber": "123456",
    "limitTotal": 1500,
    "limitAvailable": 1500.0,
    "status": true
}
```

### - Response:
```
{
    "id": 1,
    "cardOwner": {
        "buyerId": 1,
        "buyerName": "Marina"
    },
    "cardNumber": "123456",
    "limitTotal": 1500.0,
    "limitAvailable": 1500.0,
    "status": true
}
```

### **[GET] getCreditCardLimits** - */api/v1/credit-card/limits/{cardId}*

### - Request:
- @PathVariable:
  
```
cardId(long)
```

### - Response:
```
Card Number: 123456
Total limit :1500.0
Limit Available: 1000.0
```

### **[GET] getCreditCardBill** - */api/v1/credit-card/bill/{cardNumber}*

### - Request:
- @PathVariable:

```
cardNumber(String)
```

### - Response:
```
[
    {
        "cartId": 1,
        "buyer": {
            "buyerId": 1,
            "buyerName": "Marina"
        },
        "totalPrice": 80.0,
        "date": "2000-02-22",
        "orderStatus": "Finished",
        "creditCard": "123456"
    }
]
```

### **[GET] getWallet** - */api/v1/credit-card/wallet/{buyerId}*

- Request:
    - @PathVariable:

```
buyerId(long)
```

- Response:
```
[
    {
        "id": 1,
        "cardOwner": {
            "buyerId": 1,
            "buyerName": "Marina"
        },
        "cardNumber": "123456",
        "limitTotal": 1500.0,
        "limitAvailable": 1500.0,
        "status": true
    }
]
```

### **[PUT] buyCart** - */api/v1/credit-card/wallet/{buyerId}*

### - Request:
    - @PathVariable:

```
- cartId(long)
- cardNumber(String)
```

### - Response:
```
your purchase value of: 00.0 has been processed successfully, thanks for your preference!
```

### **[PUT] updateStatus** - */update_status/{cardNumber}*

### - Request:
- @PathVariable:

```
- cardNumber(String)
```

### - Response:

when credit card was unlocked
```
Card 123456 was locked, you cannot use it
```

when credit card was locked
```
Cart 123456 was unlocked, now you can use it
```
### **[POST] updateStatus** - */api/v1/payment/credit-card/{cartNumber}*

### - Request:
- @PathVariable:

```
- cardNumber(String)
```
   
- @RequestBody: 

```
{
    "payer": {
        "buyerId": 1
    },
    "value": 80.0
}
```

### - Response:


```
payment: 1
Card 123456 payment successfully completed
Your new limit available is: 1000.0
```
