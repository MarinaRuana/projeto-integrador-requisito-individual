package com.desafiofinal.praticafinal.Builder;

import java.time.LocalDate;

import com.desafiofinal.praticafinal.model.BatchStock;

public class BatchStockBuilder {

    private BatchStock batchStock;

    private BatchStockBuilder(){}

    public static BatchStockBuilder aBatchStock() {
        BatchStockBuilder builder = new BatchStockBuilder();

        builder.batchStock = new BatchStock();

        builder.batchStock.setBatchId(1l);
        builder.batchStock.setCurrentTemperature(10.0f);
        builder.batchStock.setMinimumTemperature(5.0f);
        builder.batchStock.setInitialQuantity(50);
        builder.batchStock.setCurrentQuantity(45);
        builder.batchStock.setManufacturingDate(LocalDate.now());
        builder.batchStock.setManufacturingTime(LocalDate.now());
        builder.batchStock.setDueDate(LocalDate.now());
        builder.batchStock.setInBoundOrder(null);
        builder.batchStock.setProduct(null);

        return builder;
    }

    public BatchStock create() {
        return batchStock;
    }
}