package com.desafiofinal.praticafinal.utils;

import com.desafiofinal.praticafinal.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestUtilsGenerator {


    public static InBoundOrder getNewOrder(){
        List<BatchStock> batchStockList = getBatchStockList();
        Sector sector = getSector();
        return InBoundOrder.builder()
                .orderId(0L)
                .dateTime(LocalDate.parse("2023-01-01"))
                .batchStockList(batchStockList)
                .sector(sector)
                .build();
    }

    public static InBoundOrder getOrderWithId(){
        List<BatchStock> batchStockList = getBatchStockList();
        Sector sector = getSector();
        return InBoundOrder.builder()
                .orderId(1L)
                .dateTime(LocalDate.parse("2023-01-01"))
                .batchStockList(batchStockList)
                .sector(sector)
                .build();
    }


    public static BatchStock getBatchStockAndSectorWithCapacity(){
        Product product = getProductWhitId();
        Sector sector = getSector();
        InBoundOrder inBoundOrder = new InBoundOrder();
        inBoundOrder.setOrderId(1L);
        inBoundOrder.setSector(sector);

        return BatchStock.builder()
                .batchId(1L)
                .product(product)
                .currentTemperature(1F)
                .minimumTemperature(1F)
                .initialQuantity(1)
                .currentQuantity(10)
                .manufacturingDate(LocalDate.parse("2023-01-01"))
                .dueDate(LocalDate.parse("2023-01-01"))
                .inBoundOrder(inBoundOrder)
                .build();
    }
    public static BatchStock getBatchStockWithoutCapacity(){
        Product product = getProductWhitId();
        Sector sector = getSectorWithoutCapacity();
        InBoundOrder inBoundOrder = new InBoundOrder();
        inBoundOrder.setOrderId(1L);
        inBoundOrder.setSector(sector);

        return BatchStock.builder()
                .batchId(1L)
                .product(product)
                .currentTemperature(1F)
                .minimumTemperature(1F)
                .initialQuantity(1)
                .currentQuantity(10)
                .manufacturingDate(LocalDate.parse("2023-01-01"))
                .dueDate(LocalDate.parse("2023-01-01"))
                .inBoundOrder(inBoundOrder)
                .build();
    }

    public static BatchStock getBatchStockWithoutOrder(){
        Product product = getProductWhitId();
        InBoundOrder inBoundOrder = new InBoundOrder();
        inBoundOrder.setOrderId(3L);

        return BatchStock.builder()
                .batchId(1L)
                .product(product)
                .currentTemperature(1F)
                .minimumTemperature(1F)
                .initialQuantity(1)
                .manufacturingDate(LocalDate.parse("2023-01-01"))
                .dueDate(LocalDate.parse("2023-01-01"))
                .inBoundOrder(inBoundOrder)
                .build();
    }

    public static List<BatchStock> getBatchStockList() {
        BatchStock batchStock = getBatchStockAndSectorWithCapacity();
        BatchStock batchStock1 = getBatchStockAndSectorWithCapacity();
        batchStock1.setBatchId(2L);
        BatchStock batchStock2 = getBatchStockAndSectorWithCapacity();
        batchStock2.setBatchId(3L);

        List<BatchStock> batchStockList = new ArrayList<>();
        batchStockList.add(batchStock);
        batchStockList.add(batchStock1);
        batchStockList.add(batchStock2);

        return batchStockList;
    }

    public static Product getProductWhitId(){
        Seller seller = getSeller();
        return Product.builder()
                .id(1L)
                .validateDate(LocalDate.parse("2023-01-01"))
                .price(1.0)
                .productType("cold")
                .productName("ham")
                .seller(seller)
                .build();
    }


    public static Seller getSeller(){
        return Seller.builder()
                .id(1L)
                .sellerName("Maria")
                .build();
    }

    public static Sector getSector(){
        return Sector.builder()
                .sectorId(1L)
                .category("FF")
                .capacity(1.0)
                .maxCapacity(100.0)
                .build();
    }

    public static Sector getSectorWithoutCapacity(){
        return Sector.builder()
                .sectorId(1L)
                .category("FF")
                .capacity(1.0)
                .maxCapacity(0.0)
                .build();
    }

    public static Purchase getPurchase(){
        return Purchase.builder()
                .purchaseId(1L)
                .batchStock(getBatchStockAndSectorWithCapacity())
                .pricePerProduct(1)
                .productQuantity(1)
                .build();
    }

    public static Purchase getPurchaseWithQuantityUnavailable(){
        return Purchase.builder()
                .purchaseId(1L)
                .batchStock(getBatchStockAndSectorWithCapacity())
                .pricePerProduct(1)
                .productQuantity(20)
                .build();
    }

    public static List<Purchase> getPurchaseList(){
        List<Purchase> purchaseList = new ArrayList<>();
        purchaseList.add(getPurchase());
        return purchaseList;
    }

    public static List<Purchase> getPurchaseWithoutCapacity(){
        Purchase purchase = getPurchase();
        purchase.setBatchStock(getBatchStockWithoutCapacity());
        List<Purchase> purchaseList = new ArrayList<>();
        purchaseList.add(purchase);
        return purchaseList;
    }

    public static List<Purchase> getPurchaseWhitQuantityUnavailable(){
        Purchase purchase = getPurchaseWithQuantityUnavailable();
        List<Purchase> purchaseList = new ArrayList<>();
        purchaseList.add(purchase);
        return purchaseList;
    }

    public static Cart getNewCartOpen(){
        Buyer buyer = getBuyer();
        return Cart.builder()
                .buyer(buyer)
                .listPurchase(getPurchaseList())
                .totalPrice(1)
                .date(LocalDate.parse("2023-02-02"))
                .orderStatus("Open")
                .build();
    }

    public static Cart getNewCartOpenWithoutCapacity(){
        Buyer buyer = getBuyer();
        return Cart.builder()
                .buyer(buyer)
                .listPurchase(getPurchaseWithoutCapacity())
                .totalPrice(1)
                .date(LocalDate.parse("2023-02-02"))
                .orderStatus("Open")
                .build();
    }

    public static Cart getNewCartOpenWithQuantityUnavailable(){
        Buyer buyer = getBuyer();
        return Cart.builder()
                .buyer(buyer)
                .listPurchase(getPurchaseWhitQuantityUnavailable())
                .totalPrice(1)
                .date(LocalDate.parse("2023-02-02"))
                .orderStatus("Open")
                .build();
    }

    public static Cart getCartFinished(){
        Buyer buyer = getBuyer();
        return Cart.builder()
                .cartId(1L)
                .buyer(buyer)
                .listPurchase(getPurchaseList())
                .totalPrice(1)
                .date(LocalDate.parse("2023-02-02"))
                .orderStatus("Finished")
                .build();
    }



    public static Cart getCartOpen(){
        Buyer buyer = getBuyer();
        return Cart.builder()
                .cartId(1L)
                .buyer(buyer)
                .listPurchase(getPurchaseList())
                .totalPrice(1)
                .date(LocalDate.parse("2023-02-02"))
                .orderStatus("Open")
                .build();
    }


    public static Buyer getBuyer(){
        return Buyer.builder()
                .buyerId(1L)
                .buyerName("Marina")
                .build();
    }

    public static Buyer getBuyerDifferentId(){
        return  Buyer.builder()
                .buyerId(2L)
                .buyerName("Ruanna")
                .build();
    }

    public static CreditCard getCreditCardUnlocked(){
        Buyer buyer = getBuyer();
        return CreditCard.builder()
                .id(1L)
                .idBuyer(buyer)
                .cardNumber("123456")
                .limitTotal(1000)
                .limitAvailable(500)
                .status(true)
                .build();
    }

    public static CreditCard getNewCreditCardUnlocked(){
        Buyer buyer = getBuyer();
        return CreditCard.builder()
                .idBuyer(buyer)
                .cardNumber("123456")
                .limitTotal(1000)
                .limitAvailable(500)
                .status(true)
                .build();
    }

    public static Payment getNewPayment(){
        Buyer buyer = getBuyer();
        return Payment.builder()
                .payer(buyer)
                .value(500)
                .build();
    }
    public static Payment getPayment(){
        Buyer buyer = getBuyer();
        return Payment.builder()
                .id(1L)
                .payer(buyer)
                .value(500)
                .build();
    }
    public static Payment getNewPaymentWithDifferentPayer(){
        Buyer buyer = getBuyerDifferentId();
        return Payment.builder()
                .payer(buyer)
                .value(500)
                .build();
    }
    public static Payment getPaymentWithDifferentPayer(){
        Buyer buyer = getBuyer();
        buyer.setBuyerId(2L);
        return Payment.builder()
                .id(1L)
                .payer(buyer)
                .value(500)
                .build();
    }

    public static Payment getNewPaymentValueExceeds(){
        Buyer buyer = getBuyer();
        return Payment.builder()
                .payer(buyer)
                .value(1000)
                .build();
    }
}
