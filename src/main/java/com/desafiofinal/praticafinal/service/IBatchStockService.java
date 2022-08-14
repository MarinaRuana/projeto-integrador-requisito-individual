package com.desafiofinal.praticafinal.service;

import com.desafiofinal.praticafinal.model.BatchStock;

import java.util.List;

public interface IBatchStockService {
    List<BatchStock> listBatchStockByCategory (String category) throws Exception;
}
