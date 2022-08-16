package com.desafiofinal.praticafinal.controller;

import com.desafiofinal.praticafinal.dto.PaymentDTO;
import com.desafiofinal.praticafinal.exception.ElementAlreadyExistsException;
import com.desafiofinal.praticafinal.exception.ElementNotFoundException;
import com.desafiofinal.praticafinal.exception.ExceededCapacityException;
import com.desafiofinal.praticafinal.model.Payment;
import com.desafiofinal.praticafinal.service.PaymentImpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

// TODO : DOCUMENTATIONS / JAVADOC
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentImpService paymentService;

    public PaymentController(PaymentImpService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * This route create a payment for a credit card when payer are already the credit card owner;
     * @autho Marina;
     * @param cardNumber(String), PaymentDTO containing: payer(Buyer), value(Double);
     * @return Http response status 201: CREATED, body: String;
     * @throws ElementNotFoundException - when the credit card with this card number does not exist in the database;
     * @throws ElementNotFoundException - when the payer does not exist in the database;
     * @throws ElementAlreadyExistsException - when the credit card does not belong tho the payer;
     * @throws ExceededCapacityException - when the payment value exceed de debt amount;
     * @throws ElementAlreadyExistsException - when a credit card with this card number already exists in the databases;
     * @see Payment
     */
    @PostMapping("/credit_card/{cardNumber}")
    public ResponseEntity<String> payCreditCard(@PathVariable String cardNumber, @RequestBody @Valid PaymentDTO paymentDTO){
        Payment newPayment = PaymentDTO.convertToPayment(paymentDTO);
        String response = paymentService.payCreditCard(cardNumber, newPayment);
        return ResponseEntity.ok(response);
    }
}
