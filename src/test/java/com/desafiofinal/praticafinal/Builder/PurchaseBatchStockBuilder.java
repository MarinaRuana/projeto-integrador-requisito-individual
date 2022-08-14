package com.desafiofinal.praticafinal.Builder;

import java.util.ArrayList;
import java.util.List;

import com.desafiofinal.praticafinal.model.Purchase;



public class PurchaseBatchStockBuilder {

    private Purchase purchase;

    private PurchaseBatchStockBuilder(){}

    public static PurchaseBatchStockBuilder aPurchaseBatchStockBuilder() {

        PurchaseBatchStockBuilder builder = new PurchaseBatchStockBuilder(); 

        builder.purchase = new Purchase(); 
        builder.purchase.setBatchStock(BatchStockBuilder.aBatchStock().create());
        builder.purchase.setPricePerProduct(5.50);
        builder.purchase.setProductQuantity(20);

        return builder;

    }

    public static List<Purchase> aListOfPurchaseBatchStocks() {
        ArrayList<Purchase> purchaseBatchStockList = new ArrayList<>();

        purchaseBatchStockList.add(aPurchaseBatchStockBuilder().create());

        return purchaseBatchStockList;
    }

    public Purchase create() {
        return purchase;
    }
}