# eWallet
eWallet provides a REST API that allows you to register a credit card for an existing and valid buyer, 
access the limits of an already created credit card, buy an existing and valid cart, 
access a buyer's credit cards and pay a credit card.

### - Classes:

 - [Buyer](https://github.com/MarinaRuana/projeto-integrador-requisito-individual/blob/develop/src/main/java/com/desafiofinal/praticafinal/model/Buyer.java)
 - [Cart](https://github.com/MarinaRuana/projeto-integrador-requisito-individual/blob/develop/src/main/java/com/desafiofinal/praticafinal/model/Cart.java)
 - [CreditCard](https://github.com/MarinaRuana/projeto-integrador-requisito-individual/blob/develop/src/main/java/com/desafiofinal/praticafinal/model/CreditCard.java)
 - [Payment](https://github.com/MarinaRuana/projeto-integrador-requisito-individual/blob/develop/src/main/java/com/desafiofinal/praticafinal/model/Payment.java)

### - Enhanced Entity-Relationship (EER):

![Class Diagram](https://cdn.discordapp.com/attachments/927534793892171798/1009154632964128869/tabela-pi-requisito-ndividual.png)

### - Methods:

#### CreditCard Routes - [CreditCardController](https://github.com/MarinaRuana/projeto-integrador-requisito-individual/blob/develop/src/main/java/com/desafiofinal/praticafinal/controller/CreditCardController.java)

|  HTTP  | URL MODEL                                | DESCRIPTION                                                                     |    US-CODE     |
|:------:|:-----------------------------------------|:--------------------------------------------------------------------------------|:--------------:|
| `POST` | /api/v1/credit-card/register_card        | registers a new credit card for an existing buyer user in the database          | ml-e-wallet-06 |
| `GET`  | /api/v1/credit-card/limits/{cardId}      | return a String with limitTotal and limitAvailable from a credit card           | ml-e-wallet-06 |
| `GET`  | /api/v1/credit-card/bill/{cardNumber}    | returns a collection of carts that were purchased by this credit card           | ml-e-wallet-06 |
| `GET`  | /api/v1/credit-card/wallet/{buyerId}     | returns a valid buyer's card wallet                                             | ml-e-wallet-06 |
| `PUT`  | /buy_cart/{cartId}/{cardNumber}          | registers a new credit card for an existing buyer user in the database          | ml-e-wallet-06 |
| `PUT`  | /update_status/{cardNumber}              | changes the status of the credit card from the card number                      | ml-e-wallet-06 |

#### Payment Routes - [PaymentController](https://github.com/MarinaRuana/projeto-integrador-requisito-individual/blob/develop/src/main/java/com/desafiofinal/praticafinal/controller/PaymentController.java)

|  HTTP  | URL MODEL                                | DESCRIPTION                                                                           |    US-CODE     |
|:------:|:-----------------------------------------|:--------------------------------------------------------------------------------------|:--------------:|
| `POST` | /api/v1/payment/credit-card/{cartNumber} | create a payment for a credit card when payer are already the <br/>credit card owner  | ml-e-wallet-06 |

### - Postman Collection: [eWallet - Postman Collection](/api/v1/credit-card/limits/{cardId})

**SQL query to populate the database with items needed for testing:**
```
INSERT INTO seller (id, seller_name) VALUES ('1', 'Marina');
INSERT INTO buyer (buyer_id, buyer_name) VALUES ('1', 'Yago');
INSERT INTO sector(sector_id, capacity, category, max_capacity) VALUES ('1', '10', 'FF', '1');
INSERT INTO product(id, bulk, price, product_name, id_seller, validate_date) VALUES ('1', '1', '1', '1', '1', '2022-09-01');
INSERT INTO product (id, bulk, price, product_name,id_seller, validate_date) VALUES ('2', '10', '2', '6','1','2022-07-30');
```

### - Unit test:
- [CreditCardImpServiceTest](https://github.com/MarinaRuana/projeto-integrador-requisito-individual/blob/develop/src/test/java/com/desafiofinal/praticafinal/service/CreditCardImpServiceTest.java) - 100% coverage
- [PaymentImpServiceTest](https://github.com/MarinaRuana/projeto-integrador-requisito-individual/blob/develop/src/test/java/com/desafiofinal/praticafinal/service/PaymentImpServiceTest.java) - 100% coverage

- total of service layer lines - 70%
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
### **[PUT] updateStatus** - */api/v1/payment/credit-card/{cartNumber}*

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
