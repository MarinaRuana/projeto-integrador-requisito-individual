package com.desafiofinal.praticafinal.service;

import com.desafiofinal.praticafinal.model.Product;

import java.util.List;

public interface IProductService {
    Product saveProduct(Product product);

    List<Product> listAllProducts();

}
