package com.desafiofinal.praticafinal.service;

import com.desafiofinal.praticafinal.exception.ElementAlreadyExistsException;
import com.desafiofinal.praticafinal.exception.ElementNotFoundException;
import com.desafiofinal.praticafinal.exception.ExceededCapacityException;
import com.desafiofinal.praticafinal.model.Buyer;
import com.desafiofinal.praticafinal.model.CreditCard;
import com.desafiofinal.praticafinal.model.Payment;
import com.desafiofinal.praticafinal.repository.BuyerRepo;
import com.desafiofinal.praticafinal.repository.CreditCardRepo;
import com.desafiofinal.praticafinal.repository.PaymentRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentImpService implements IPaymentService{

    private final PaymentRepo paymentRepo;

    private final CreditCardRepo creditCardRepo;

    private final BuyerRepo buyerRepo;

    public PaymentImpService(PaymentRepo paymentRepo, CreditCardRepo creditCardRepo, BuyerRepo buyerRepo) {
        this.paymentRepo = paymentRepo;
        this.creditCardRepo = creditCardRepo;
        this.buyerRepo = buyerRepo;
    }

    @Override
    public String payCreditCard(String cardNumber, Payment payment){
        CreditCard foundCreditCard = verifyCreditCardNumberExists(cardNumber);
        Buyer foundPayer = verifyBuyer(payment.getPayer().getBuyerId());
        payment.setPayer(foundPayer);
        verifyPayerAreBuyer(payment.getPayer(), foundCreditCard.getIdBuyer());
        verifyPaymentIsAllowed(payment, foundCreditCard);
        payment.setCreditCard(foundCreditCard);
        double pay = foundCreditCard.getLimitAvailable() + payment.getValue();
        foundCreditCard.setLimitAvailable(pay);
        creditCardRepo.save(foundCreditCard);
        paymentRepo.save(payment);
        return "payment: " + payment.getId() + "\n" +
                "Card " + foundCreditCard.getCardNumber() + " payment successfully completed" + "\n" +
                "Your new limit available is: " + foundCreditCard.getLimitAvailable();
    }

    private void verifyPaymentIsAllowed(Payment payment, CreditCard foundCreditCard) {
        double billValue = foundCreditCard.getLimitTotal() - foundCreditCard.getLimitAvailable();
        if(billValue < payment.getValue()){
           throw new ExceededCapacityException("payment exceeds debt amount");
        }
    }

    private void verifyPayerAreBuyer(Buyer payer, Buyer cardOwner) {
        if(payer.getBuyerId() != cardOwner.getBuyerId()){
            throw new ElementAlreadyExistsException("Credit card does not belong to this payer");
        }
    }

    private CreditCard verifyCreditCardNumberExists(String cardNumber) {
        Optional<CreditCard> foundCreditCard = Optional.ofNullable(creditCardRepo.findByCardNumber(cardNumber));
        if(foundCreditCard.isEmpty()) {
            throw new ElementNotFoundException("Credit card does not exists");
        }
        return foundCreditCard.get();
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
