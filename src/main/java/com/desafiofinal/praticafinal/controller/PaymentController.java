package com.desafiofinal.praticafinal.controller;

import com.desafiofinal.praticafinal.dto.PaymentDTO;
import com.desafiofinal.praticafinal.model.Payment;
import com.desafiofinal.praticafinal.service.PaymentImpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentImpService paymentService;

    public PaymentController(PaymentImpService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/credit_card/{cardNumber}")
    public ResponseEntity<String> payCreditCard(@PathVariable String cardNumber, @RequestBody PaymentDTO paymentDTO){
        Payment newPayment = PaymentDTO.convertToPayment(paymentDTO);
        String response = paymentService.payCreditCard(cardNumber, newPayment);
        return ResponseEntity.ok(response);
    }
}
