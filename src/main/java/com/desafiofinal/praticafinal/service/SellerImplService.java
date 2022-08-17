package com.desafiofinal.praticafinal.service;

import com.desafiofinal.praticafinal.exception.ElementAlreadyExistsException;
import com.desafiofinal.praticafinal.model.Seller;
import com.desafiofinal.praticafinal.repository.ISellerRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class SellerImplService implements ISellerService{
    private final ISellerRepo repo;

    public SellerImplService(ISellerRepo repo) {
        this.repo = repo;
    }

    @Override
    public Seller saveSeller(Seller seller) {
        Optional<Seller> foundSeller = repo.findById(seller.getId());
        if(foundSeller.isPresent()){
            throw new ElementAlreadyExistsException("Seller already exists");
        }
        return repo.save(seller);
    }
}
