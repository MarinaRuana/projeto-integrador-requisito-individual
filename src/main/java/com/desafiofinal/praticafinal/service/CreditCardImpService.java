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
public class CreditCardImpService implements ICreditCardService{

    private final CreditCardRepo creditCardRepo;

    private final BuyerRepo buyerRepo;

    private final CartRepo cartRepo;

    public CreditCardImpService(CreditCardRepo creditCardRepo, BuyerRepo buyerRepo, CartRepo cartRepo, PurchaseRepo purchaseRepo) {
        this.creditCardRepo = creditCardRepo;
        this.buyerRepo = buyerRepo;
        this.cartRepo = cartRepo;
    }

    @Override
    public CreditCard registerCard(CreditCard newCreditCard){
        verifyLimits(newCreditCard);
        verifyCreditCardNumberExists(newCreditCard.getCardNumber());
        Buyer foundBuyer = verifyBuyer(newCreditCard.getIdBuyer().getBuyerId());
        newCreditCard.setIdBuyer(foundBuyer);
        return creditCardRepo.save(newCreditCard);
    }

    @Override
    public String getCreditCardLimits(Long id){
        Optional<CreditCard> foundCreditCard = creditCardRepo.findById(id);

        if (foundCreditCard.isEmpty()) {
            throw new ElementNotFoundException("Credit card does not exists");
        }

        return "Card Number: "+ foundCreditCard.get().getCardNumber() + "\n" +
                "Total limit :" + foundCreditCard.get().getLimitTotal() + "\n" +
                "Limit Available: " + foundCreditCard.get().getLimitAvailable();
    }

    @Override
    public String buyCart(Long cartId, String cardNumber){

        CreditCard creditCard = verifyCreditCardNumberNotExists(cardNumber);
        verifyStatus(creditCard);
        Cart cart = verifyCartExists(cartId);

        if(creditCard.getLimitAvailable() < cart.getTotalPrice()){
           throw new ExceededCapacityException("Purchase value exceeds the card's current available limit");
        }
        double value = creditCard.getLimitAvailable() - cart.getTotalPrice();
        double cartValue = creditCard.getLimitAvailable() - value;
        creditCard.setLimitAvailable(value);
        cart.setCreditCard(creditCard);
        cart.setOrderStatus("Finished");
        cartRepo.save(cart);
        creditCardRepo.save(creditCard);

        return "your purchase value of: " + cartValue + " has been processed successfully, thanks for your preference!";
    }

    @Override
    public List<Cart> getCardBill(String cardNumber){
        CreditCard foundCard = verifyCreditCardNumberNotExists(cardNumber);
        return foundCard.getCartList();
    }

    @Override
    public String updateCardStatus(String cardNumber){
        CreditCard foundCreditCard = verifyCreditCardNumberNotExists(cardNumber);
        if(foundCreditCard.isStatus()) {
            foundCreditCard.setStatus(false);
            creditCardRepo.save(foundCreditCard);
            return "Card " + foundCreditCard.getCardNumber() + " was locked, you cannot use it";
        }

        foundCreditCard.setStatus(true);
        creditCardRepo.save(foundCreditCard);
        return "Cart " + foundCreditCard.getCardNumber() + " was unlocked, now you can use it";
    }

    @Override
    public List<CreditCard> getWallet(long buyerId){
        Buyer foundBuyer = verifyBuyer(buyerId);
        return foundBuyer.getCreditCards();
    }

    private void verifyStatus(CreditCard creditCard) {
        if(!creditCard.isStatus()){
            throw new ExceededCapacityException("This credit card is blocked");
        }
    }
    private void verifyLimits(CreditCard newCreditCard) {
        if(newCreditCard.getLimitTotal() < newCreditCard.getLimitAvailable()){
            throw new ExceededCapacityException("Total limit cannot be less than the available limit");
        }
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

    private CreditCard verifyCreditCardNumberNotExists(String cardNumber) {
        Optional<CreditCard> foundCreditCard = Optional.ofNullable(creditCardRepo.findByCardNumber(cardNumber));
        if(foundCreditCard.isEmpty()) {
            throw new ElementNotFoundException("Credit card does not exists");
        }
        return foundCreditCard.get();
    }

    private void verifyCreditCardNumberExists(String cardNumber) {
        Optional<CreditCard> foundCreditCard = Optional.ofNullable(creditCardRepo.findByCardNumber(cardNumber));
        if(foundCreditCard.isPresent()) {
            throw new ElementAlreadyExistsException("Credit card already exists");
        }
    }

    private Buyer verifyBuyer(long buyerId) {
        Optional<Buyer> foundBuyer = buyerRepo.findById(buyerId);
        if (foundBuyer.isPresent()) {
            return foundBuyer.get();
        } else {
            throw new ElementNotFoundException("Buyer does not exists");
        }
    }


}
