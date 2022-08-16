package com.desafiofinal.praticafinal.controller;

import com.desafiofinal.praticafinal.dto.CreditCardDTO;
import com.desafiofinal.praticafinal.dto.requestResponseDto.CartResponseDTO;
import com.desafiofinal.praticafinal.exception.*;
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

    /**
     * This route create a new register of a credit card from a buyer;
     * @autho Marina;
     * @param newCreditCard(CreditCardDTO) containing: cardOwner(BuyerDTO), cardName(String),limitTotal(Double), limitAvailable(Double), status(Boolean);
     * @return Http response status 201: CREATED, body: CreditCardDTO;
     * @throws ExceededCapacityException - when the value of the available limit is greater than the total limit;
     * @throws ElementAlreadyExistsException - when a credit card with this card number already exists in the databases;
     * @throws ElementNotFoundException - when the buyer does not exist in the database;
     * @see CreditCard
     */
    @PostMapping("/register_card")
    public ResponseEntity<CreditCardDTO> registerCard(@RequestBody @Valid CreditCardDTO newCreditCard){
        CreditCard newCard = CreditCardDTO.convertToCreditCard(newCreditCard);
        CreditCard creditCard = creditCardService.registerCard(newCard);
        CreditCardDTO response = new CreditCardDTO(creditCard);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * This route return a String with limitTotal and limitAvailable from a credit card
     * @autho Marina;
     * @param cardId(long);
     * @return Http response status ok, body: String;
     * @throws ElementNotFoundException - when the credit card with this id do not exist in  the databases;
     * @see CreditCard
     */
    @GetMapping("/limits/{cardId}")
    public ResponseEntity<String> getCreditCardLimits(@PathVariable long cardId){
        String response = creditCardService.getCreditCardLimits(cardId);
        return ResponseEntity.ok(response);
    }

    /**
     * returns to a collection of carts that were purchased by this credit card
     * @autho Marina;
     * @param cardNumber(String);
     * @return Http response status ok, body: List<CartResponseDTO>;
     * @throws ElementNotFoundException - when the credit card with this id does not exist in the databases;
     * @see CartResponseDTO
     */
    @GetMapping("/bill/{cardNumber}")
    public ResponseEntity<List<CartResponseDTO>> getCreditCardBill(@PathVariable String cardNumber){
        List<CartResponseDTO> response = CartResponseDTO.convertListToDTO(creditCardService.getCardBill(cardNumber));
        return ResponseEntity.ok(response);
    }

    /**
     * this route returns a valid buyer's card wallet - wallet is a collection of credit cards from the same buyer
     * @autho Marina;
     * @param buyerId(long);
     * @return Http response status ok, body: List<CreditCardDTO>;
     * @throws ElementNotFoundException - when the buyer with that id does not exist in databases;
     * @see CartResponseDTO
     */
    @GetMapping("/wallet/{buyerId}")
    public ResponseEntity<List<CreditCardDTO>> getWallet(@PathVariable long buyerId){
        List<CreditCard> wallet = creditCardService.getWallet(buyerId);
        List<CreditCardDTO> response = CreditCardDTO.covertListToDTO(wallet);
        return ResponseEntity.ok(response);
    }

    /**
     * this route buy an existing cart open, passing its status to finished and debiting the total value of this cart from the available limit of the credit card
     * @autho Marina;
     * @param cartId(long), cardNumber(String);
     * @return Http response status ok, body: String;
     * @throws ElementNotFoundException - when a credit card with this card number does not exist in the databases;;
     * @throws ExceededCapacityException - when a credit card are locked (CreditCard.status = false) ;
     * @throws ElementNotFoundException - when a cart with this id does not exist in the databases;
     * @throws ElementAlreadyExistsException - when a cart with this id are already finished (cart.status = "Finished");
     * @throws ExceededCapacityException - when the available limit of the credit card is less than the total value of the cart;
     */
    @PutMapping("/buy_cart/{cartId}/{cardNumber}")
    public ResponseEntity<String> buyCart(@PathVariable Long cartId, @PathVariable String cardNumber){
        String response = creditCardService.buyCart(cartId, cardNumber);
        return ResponseEntity.ok(response);
    }

    /**
     * this route changes the status of the credit card from the card number
     * @autho Marina;
     * @param cardNumber(String);
     * @return Http response status ok, body: String;
     * @throws ElementNotFoundException - when a credit card with this card number does not exist in databases;
     */
    @PutMapping("/update_status/{cardNumber}")
    public ResponseEntity<String> updateStatus(@PathVariable String cardNumber){
        String response = creditCardService.updateCardStatus(cardNumber);
        return ResponseEntity.ok(response);
    }

}
