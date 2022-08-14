package com.desafiofinal.praticafinal.service;


import com.desafiofinal.praticafinal.model.InBoundOrder;

public interface IinBoundOrderService {
    InBoundOrder saveInBoundOrder(InBoundOrder newOrder);
    InBoundOrder updateInBoundOrder(InBoundOrder updateOrder);
}
