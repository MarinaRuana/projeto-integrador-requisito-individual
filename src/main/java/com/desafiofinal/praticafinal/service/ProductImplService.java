package com.desafiofinal.praticafinal.service;

import com.desafiofinal.praticafinal.exception.ElementNotFoundException;
import com.desafiofinal.praticafinal.model.Product;
import com.desafiofinal.praticafinal.model.Seller;
import com.desafiofinal.praticafinal.repository.IProductRepo;
import com.desafiofinal.praticafinal.repository.ISellerRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImplService implements IProductService{

    private final IProductRepo repo;
    private final ISellerRepo sellerRepo;


    public ProductImplService(IProductRepo repo, ISellerRepo sellerRepo) {
        this.repo = repo;
        this.sellerRepo = sellerRepo;
    }

    @Override
    public Product saveProduct(Product newProduct) {
        Seller foundSeller = verifySeller(newProduct);
        newProduct.setSeller(foundSeller);
        return repo.save(newProduct);
    }

    private Seller verifySeller(Product product) {
        return sellerRepo
                .findById(product.getSeller().getId())
                .orElseThrow(() -> new ElementNotFoundException("Seller does not exist"));
    }

    @Override
    public List<Product> listAllProducts (){
        return repo.findAll();
    }

}
