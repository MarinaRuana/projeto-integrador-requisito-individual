package com.desafiofinal.praticafinal.controller;

import com.desafiofinal.praticafinal.dto.CreditCardDTO;
import com.desafiofinal.praticafinal.dto.requestResponseDto.CartResponseDTO;
import com.desafiofinal.praticafinal.model.CreditCard;
import com.desafiofinal.praticafinal.service.CreditCardImpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/credit_card")
public class CreditCardController {

    private final CreditCardImpService creditCardService;

    public CreditCardController(CreditCardImpService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @PostMapping("/register_card")
    public ResponseEntity<CreditCardDTO> registerCard(@RequestBody @Valid CreditCardDTO newCreditCard){
        CreditCard newCard = CreditCardDTO.convertToCreditCard(newCreditCard);
        CreditCard creditCard = creditCardService.registerCard(newCard);
        CreditCardDTO response = new CreditCardDTO(creditCard);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/limits/{id}")
    public ResponseEntity<String> getCreditCardLimits(@PathVariable long id){
        String response = creditCardService.getCreditCardLimits(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/bill/{cardNumber}")
    public ResponseEntity<List<CartResponseDTO>> getCreditCardBill(@PathVariable String cardNumber){
        List<CartResponseDTO> response = CartResponseDTO.convertListToDTO(creditCardService.getCardBill(cardNumber));
        return ResponseEntity.ok(response);
    }
    @GetMapping("/wallet/{buyerId}")
    public ResponseEntity<List<CreditCardDTO>> getWallet(@PathVariable long buyerId){
        List<CreditCard> wallet = creditCardService.getWallet(buyerId);
        List<CreditCardDTO> response = CreditCardDTO.covertListToDTO(wallet);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/buy_cart/{cartId}/{cardNumber}")
    public ResponseEntity<String> buyCart(@PathVariable Long cartId, @PathVariable String cardNumber){
        String response = creditCardService.buyCart(cartId, cardNumber);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update_status/{cardNumber}")
    public ResponseEntity<String> updateStatus(@PathVariable String cardNumber){
        String response = creditCardService.updateCardStatus(cardNumber);
        return ResponseEntity.ok(response);
    }

}
