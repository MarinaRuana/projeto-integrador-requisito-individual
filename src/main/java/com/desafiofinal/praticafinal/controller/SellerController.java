package com.desafiofinal.praticafinal.controller;

import com.desafiofinal.praticafinal.dto.SellerDTO;
import com.desafiofinal.praticafinal.model.Seller;
import com.desafiofinal.praticafinal.service.ISellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class holds all endpoints related to the seller
 * @author Amanda
 * @version 1.0.0
 */

@RestController
@RequestMapping("/api/v1/fresh-products/seller")
public class SellerController {

    private final ISellerService service;

    public SellerController(ISellerService service) {
        this.service = service;
    }
    /**
     * This route creates a new seller
     * @param seller id(Long), SellerName(String), ProductList(List of products)
     * @return HTML Response 201: Created
     */
    @PostMapping
    public ResponseEntity<SellerDTO> insertSeller(@RequestBody SellerDTO seller){
        Seller newSeller = SellerDTO.convertToSeller(seller);
        Seller savedSeller = service.saveSeller(newSeller);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SellerDTO(savedSeller));
    }
}
