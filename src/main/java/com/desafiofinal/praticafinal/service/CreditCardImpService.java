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
    //TODO:
    // Verifica se o limite disponivel é menor do que o limite total - OK
    // Verifica se o numero do cartão ja existe                      - OK
    // verifica se o buyer existe                                    - OK
    // verifica se o cartão ja possui um id                          - OK
    // salva o cartão                                                - OK
    // Validações                                                    - A fazer
    // Testes Unitários                                              - A fazer
    public CreditCard registerCard(CreditCard newCreditCard){
        verifyLimits(newCreditCard);
        verifyCreditCardNumberExists(newCreditCard.getCardNumber());
        verifyBuyer(newCreditCard.getIdBuyer(), newCreditCard);

        Optional<CreditCard> foundCreditCard = creditCardRepo.findById(newCreditCard.getId());
        if (foundCreditCard.isPresent()) {
            throw new ElementAlreadyExistsException("Credit card already register");
        }
        return creditCardRepo.save(newCreditCard);
    }

    // TODO:
    //  Verifica se o cartão existe e retorna ele                     - OK
    //  Retorna String com o limite total e o disponivel deste cartão - OK
    //  Validações                                                    - A fazer
    //  Testes Unitários                                              - A fazer
    public String getCreditCardLimits(Long id){
        Optional<CreditCard> foundCreditCard = creditCardRepo.findById(id);

        if (foundCreditCard.isEmpty()) {
            throw new ElementNotFoundException("Credit card does not exists");
        }

        return "Card Number: "+ foundCreditCard.get().getCardNumber() + "\n" +
                "Total limit :" + foundCreditCard.get().getLimitTotal() + "\n" +
                "Limit Available: " + foundCreditCard.get().getLimitAvailable();
    }

    // TODO:
    //  Verifica se o cartão existe pelo numero                         - OK
    //  Verifica se o cartão esta com status = true (unlocked)          - OK
    //  Verifica se o status do carrinho esta Open                      - OK
    //  Verifica se o limite disponivel é menor que o valor do carrinho - OK
    //  Subtrai o valor do carrinho do limite disponivel                - OK
    //  Muda o OrderStatus do carrinho para Finished                    - OK
    //  Salva a mudancas no carrinho                                    - OK
    //  Salva as mudancas no cartao Retorna String de successfully      - OK
    //  Validações                                                      - A fazer
    //  Testes Unitários                                                - A fazer
    public String buyCart(Long cartId, Long cardNumber){

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

        return "your purchase value of: " + cartValue + " has been processed successfully, thanks your preference!";
    }

    //TODO:
    // Procura o cartão de credito a partir do seu cardNumber - OK
    // Retorna A lista de carts compradas por esse cartão     - OK
    // Validações                                            - A fazer
    // Testes Unitários                                      - A fazer
    public List<Cart> getCardBill(Long cardNumber){
        CreditCard foundCard = verifyCreditCardNumberNotExists(cardNumber);
        return foundCard.getCartList();
    }

    private void verifyStatus(CreditCard creditCard) {
        if(!creditCard.isStatus()){
            throw new ExceededCapacityException("This credit card is blocked");
        }
    }
    private void verifyLimits(CreditCard newCreditCard) {
        if(newCreditCard.getLimitTotal() < newCreditCard.getLimitAvailable()){
            throw new ElementAlreadyExistsException("Total limit cannot be less than the available limit");
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

    private CreditCard verifyCreditCardNumberNotExists(Long cardNumber) {
        CreditCard foundCreditCard = creditCardRepo.findByCardNumber(cardNumber);
        if(foundCreditCard.getId() <= 0) {
            throw new ElementNotFoundException("Credit card does not exists");
        }
        return foundCreditCard;
    }

    private void verifyCreditCardNumberExists(Long cardNumber) {
        Optional<CreditCard> foundCreditCard = Optional.ofNullable(creditCardRepo.findByCardNumber(cardNumber));
        if(foundCreditCard.isPresent()) {
            throw new ElementAlreadyExistsException("Credit card already exists");
        }
    }


    private Buyer verifyBuyer(Buyer buyer, CreditCard creditCard) {
        Optional<Buyer> foundBuyer = buyerRepo.findById(buyer.getBuyerId());
        if (foundBuyer.isPresent()) {
            creditCard.setIdBuyer(foundBuyer.get());
            creditCardRepo.saveAll(foundBuyer.get().getCreditCards());
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
