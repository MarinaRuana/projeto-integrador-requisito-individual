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
    //TODO:
    // Verifica se o limite disponivel é menor do que o limite total - OK
    // Verifica se o numero do cartão ja existe                      - OK
    // verifica se o buyer existe                                    - OK
    // verifica se o cartão ja possui um id                          - OK
    // salva o cartão                                                - OK
    // Validações                                                    - A fazer
    // Testes Unitários                                              - A fazer
    @Override
    public CreditCard registerCard(CreditCard newCreditCard){
        verifyLimits(newCreditCard);
        verifyCreditCardNumberExists(newCreditCard.getCardNumber());
        Buyer foundBuyer = verifyBuyer(newCreditCard.getIdBuyer().getBuyerId());
        newCreditCard.setIdBuyer(foundBuyer);

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
    @Override
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

        return "your purchase value of: " + cartValue + " has been processed successfully, thanks for your preference!";
    }

    //TODO:
    // Procura o cartão de credito a partir do seu cardNumber - OK
    // Retorna A lista de carts compradas por esse cartão     - OK
    // Validações                                            - A fazer
    // Testes Unitários                                      - A fazer
    @Override
    public List<Cart> getCardBill(Long cardNumber){
        CreditCard foundCard = verifyCreditCardNumberNotExists(cardNumber);
        return foundCard.getCartList();
    }

    //TODO:
    // Verifica se o cartao existe a partir de seu numero       - OK
    // Muda o status do cartao Salva o cartao como novo status  - OK
    // Retorna mensagem informando o status atual do cartao     - OK
    // Validações                                            - A fazer
    // Testes Unitários                                      - A fazer
    @Override
    public String updateCardStatus(Long cardNumber){
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

    //TODO:
    // Achar buyer pelo id                      - OK
    // Retornar a lista de Cartoes desse buyer  - OK
    // Validações                               - A fazer
    // Testes Unitários                         - A fazer
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
        Optional<CreditCard> foundCreditCard = Optional.ofNullable(creditCardRepo.findByCardNumber(cardNumber));
        if(foundCreditCard.isEmpty()) {
            throw new ElementNotFoundException("Credit card does not exists");
        }
        return foundCreditCard.get();
    }

    private void verifyCreditCardNumberExists(Long cardNumber) {
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
