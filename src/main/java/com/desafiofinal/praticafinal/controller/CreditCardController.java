package com.desafiofinal.praticafinal.controller;

import com.desafiofinal.praticafinal.dto.CartDto;
import com.desafiofinal.praticafinal.dto.CreditCardDTO;
import com.desafiofinal.praticafinal.dto.requestResponseDto.CartResponseDTO;
import com.desafiofinal.praticafinal.model.Cart;
import com.desafiofinal.praticafinal.model.CreditCard;
import com.desafiofinal.praticafinal.service.CreditCardImpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class CreditCardController {

    private CreditCardImpService creditCardService;

    public CreditCardController(CreditCardImpService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @PostMapping("/register_card")
    public ResponseEntity<CreditCardDTO> registerCard(@RequestBody CreditCardDTO newCreditCard){
        CreditCard newCard = CreditCardDTO.convertToCreditCard(newCreditCard);
        CreditCard creditCard = creditCardService.registerCard(newCard);
        CreditCardDTO response = new CreditCardDTO(creditCard);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/credit_card/limits/{id}")
    public ResponseEntity<String> getCreditCardLimits(@PathVariable long id){
        String response = creditCardService.getCreditCardLimits(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/credit_card/bill/{cardNumber}")
    public ResponseEntity<List<CartResponseDTO>> getCreditCardBill(@PathVariable long cardNumber){
        List<CartResponseDTO> response = CartResponseDTO.convertListToDTO(creditCardService.getCardBill(cardNumber));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/buyCart/{cartId}/{cardNumber}")
    public ResponseEntity<String> buyCart(@PathVariable Long cartId, @PathVariable Long cardNumber){
        String response = creditCardService.buyCart(cartId, cardNumber);
        return ResponseEntity.ok(response);
    }

}
