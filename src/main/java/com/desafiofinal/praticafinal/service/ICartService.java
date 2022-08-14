package com.desafiofinal.praticafinal.service;

import com.desafiofinal.praticafinal.model.BatchStock;
import com.desafiofinal.praticafinal.model.Cart;

import java.util.List;

public interface ICartService {
    Double createPurchase(Cart cart);

    List<BatchStock> getProducts(long purchaseId);

    String updateStatus(long purchaseId);
}
