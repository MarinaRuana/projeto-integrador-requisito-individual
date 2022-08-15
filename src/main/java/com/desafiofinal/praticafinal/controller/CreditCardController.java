package com.desafiofinal.praticafinal.controller;

import com.desafiofinal.praticafinal.model.CreditCard;
import com.desafiofinal.praticafinal.repository.CreditCardRepo;
import com.desafiofinal.praticafinal.service.CreditCardImpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class CreditCardController {

    private CreditCardImpService creditCardService;

    public CreditCardController(CreditCardImpService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @PostMapping("/register_card")
    public ResponseEntity<CreditCard> registerCard(@RequestBody CreditCard newCreditCard){
        return ResponseEntity.status(HttpStatus.CREATED).body(creditCardService.registerCard(newCreditCard));
    }

}
