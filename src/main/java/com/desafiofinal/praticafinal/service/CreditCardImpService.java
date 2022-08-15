package com.desafiofinal.praticafinal.service;

import com.desafiofinal.praticafinal.exception.ElementAlreadyExistsException;
import com.desafiofinal.praticafinal.exception.ElementNotFoundException;
import com.desafiofinal.praticafinal.exception.ExceededCapacityException;
import com.desafiofinal.praticafinal.model.*;
import com.desafiofinal.praticafinal.repository.BuyerRepo;
import com.desafiofinal.praticafinal.repository.CartRepo;
import com.desafiofinal.praticafinal.repository.CreditCardRepo;
import com.desafiofinal.praticafinal.repository.PurchaseRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditCardImpService {

    private CreditCardRepo creditCardRepo;

    private BuyerRepo buyerRepo;

    private CartRepo cartRepo;

    private PurchaseRepo purchaseRepo;

    public CreditCardImpService(CreditCardRepo creditCardRepo, BuyerRepo buyerRepo, CartRepo cartRepo, PurchaseRepo purchaseRepo) {
        this.creditCardRepo = creditCardRepo;
        this.buyerRepo = buyerRepo;
        this.cartRepo = cartRepo;
        this.purchaseRepo = purchaseRepo;
    }

    // TODO : Calculo do limite disponivel.
    // TODO: Verificar numero do cartão (unico).
    public CreditCard registerCard(CreditCard newCreditCard){
        Buyer foundBuyer = verifyBuyer(newCreditCard.getIdBuyer(), newCreditCard);
        newCreditCard.setIdBuyer(foundBuyer);

        Optional<CreditCard> foundCreditCard = creditCardRepo.findById(newCreditCard.getId());

        if (foundCreditCard.isPresent()) {
            throw new ElementAlreadyExistsException("Credit card already register");
        }
        creditCardRepo.saveAll(foundBuyer.getCreditCards());
        return creditCardRepo.save(newCreditCard);
    }

    public CreditCard getCreditCardLimits(Long id){
        Optional<CreditCard> foundCreditCard = creditCardRepo.findById(id);

        if (foundCreditCard.isEmpty()) {
            throw new ElementNotFoundException("Credit card does not exists");
        }
        return foundCreditCard.get();
    }

    public String buyCart(Long cartId, Long cardNumber){

        CreditCard creditCard = verifyCreditCardNumber(cardNumber);
        Cart cart = verifyCartExists(cartId);

        if(creditCard.getLimitAvailable() < cart.getTotalPrice()){
           throw new ExceededCapacityException("Purchase value exceeds the card's current available limit");
        }
        double value = creditCard.getLimitAvailable() - cart.getTotalPrice();
        double cartValue = creditCard.getLimitAvailable() - value;
        creditCard.setLimitAvailable(value);
        cart.setOrderStatus("Finished");
        cartRepo.save(cart);
        creditCardRepo.save(creditCard);

        return "your purchase value of: " + cartValue + " has been processed successfully, thanks your preference!";
    }

    private Cart verifyCartExists(Long cartId) {
        Optional<Cart> foundCart = cartRepo.findById(cartId);
        if(foundCart.isEmpty()){
            throw new ElementNotFoundException("Cart does not exists");
        }

        if(foundCart.get().getOrderStatus().equalsIgnoreCase("finished")){
            throw new ElementAlreadyExistsException("This cart already finished");
        }
        return foundCart.get();
    }

    private CreditCard verifyCreditCardNumber(Long cardNumber) {
        CreditCard foundCreditCard = creditCardRepo.findByCardNumber(cardNumber);
        if(foundCreditCard.getId() <= 0){
            throw new ElementNotFoundException("Credit card does not exists");
        }
        return foundCreditCard;
    }

    private Buyer verifyBuyer(Buyer buyer, CreditCard creditCard) {
        Optional<Buyer> foundBuyer = buyerRepo.findById(buyer.getBuyerId());
        if (foundBuyer.isPresent()) {
            creditCard.setIdBuyer(foundBuyer.get());
            return foundBuyer.get();
        } else {
            throw new ElementNotFoundException("Buyer does not exists");
        }
    }

    private void verifyCreditCardBelongsToBuyer(List<CreditCard> creditCardList, Buyer buyer) {
        for(CreditCard creditCard : creditCardList) {
            Optional<CreditCard> foundCreditCard = creditCardRepo.findById(creditCard.getId());
            if(foundCreditCard.isPresent()) {
                if ((foundCreditCard.get().getIdBuyer().getBuyerId() == buyer.getBuyerId())) {
                    creditCard.setIdBuyer(buyer);
                } else {
                    throw new ElementNotFoundException("Credit Card does not belongs to this buyer");
                }
            } else {
                throw new ElementNotFoundException("Credit Card does not exists");
            }
        }
    }

}
