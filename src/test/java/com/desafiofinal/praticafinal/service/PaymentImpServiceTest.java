package com.desafiofinal.praticafinal.service;

import com.desafiofinal.praticafinal.exception.ElementAlreadyExistsException;
import com.desafiofinal.praticafinal.exception.ElementNotFoundException;
import com.desafiofinal.praticafinal.exception.ExceededCapacityException;
import com.desafiofinal.praticafinal.model.CreditCard;
import com.desafiofinal.praticafinal.model.Payment;
import com.desafiofinal.praticafinal.repository.BuyerRepo;
import com.desafiofinal.praticafinal.repository.CreditCardRepo;
import com.desafiofinal.praticafinal.repository.PaymentRepo;
import com.desafiofinal.praticafinal.utils.TestUtilsGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PaymentImpServiceTest {

    @InjectMocks
    PaymentImpService paymentService;

    @Mock
    PaymentRepo paymentRepo;

    @Mock
    CreditCardRepo creditCardRepo;

    @Mock
    BuyerRepo buyerRepo;

    @Test
    void payCreditCard_whenCardExistAndBelongsToPayer() {
        BDDMockito.when(creditCardRepo.findByCardNumber(ArgumentMatchers.anyString()))
                .thenReturn(TestUtilsGenerator.getCreditCardUnlocked());
        BDDMockito.when(buyerRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getBuyer()));
        BDDMockito.when(paymentRepo.save(ArgumentMatchers.any(Payment.class)))
                .thenReturn(TestUtilsGenerator.getPayment());

        CreditCard creditCard = TestUtilsGenerator.getCreditCardUnlocked();
        Payment newPayment = TestUtilsGenerator.getNewPayment();
        String response = paymentService.payCreditCard(creditCard.getCardNumber(),newPayment);

        assertThat(response).contains("payment");
        assertThat(response).isNotEmpty();
        assertThat(response).containsIgnoringCase("Your new limit available is");
        Mockito.verify(creditCardRepo, Mockito.atLeastOnce()).findByCardNumber(creditCard.getCardNumber());
        Mockito.verify(paymentRepo, Mockito.atLeastOnce()).save(newPayment);
    }

    @Test
    void payCreditCard_throwException_whenPaymentsExceeds() {
        BDDMockito.when(creditCardRepo.findByCardNumber(ArgumentMatchers.anyString()))
                .thenReturn(TestUtilsGenerator.getCreditCardUnlocked());
        BDDMockito.when(buyerRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getBuyer()));
        BDDMockito.when(paymentRepo.save(ArgumentMatchers.any(Payment.class)))
                .thenReturn(TestUtilsGenerator.getPayment());

        CreditCard creditCard = TestUtilsGenerator.getCreditCardUnlocked();
        Payment newPayment = TestUtilsGenerator.getNewPaymentValueExceeds();
        Assertions.assertThatThrownBy(()
                        ->  paymentService.payCreditCard(creditCard.getCardNumber(),newPayment))
                .isInstanceOf(ExceededCapacityException.class)
                .hasMessageContaining("payment exceeds debt amount");
    }

    @Test
    void payCreditCard_throwException_cartNotBelongsToPayer() {
        BDDMockito.when(creditCardRepo.findByCardNumber(ArgumentMatchers.anyString()))
                .thenReturn(TestUtilsGenerator.getCreditCardUnlocked());
        BDDMockito.when(buyerRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getBuyerDifferentId()));
        BDDMockito.when(paymentRepo.save(ArgumentMatchers.any(Payment.class)))
                .thenReturn(TestUtilsGenerator.getPaymentWithDifferentPayer());

        CreditCard creditCard = TestUtilsGenerator.getCreditCardUnlocked();
        Payment newPayment = TestUtilsGenerator.getNewPaymentWithDifferentPayer();

        Assertions.assertThatThrownBy(()
                        ->  paymentService.payCreditCard(creditCard.getCardNumber(),newPayment))
                .isInstanceOf(ElementAlreadyExistsException.class)
                .hasMessageContaining("Credit card does not belong to this payer");
    }

    @Test
    void payCreditCard_throwException_cardNotExists() {
        BDDMockito.when(buyerRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getBuyer()));
        BDDMockito.when(paymentRepo.save(ArgumentMatchers.any(Payment.class)))
                .thenReturn(TestUtilsGenerator.getPaymentWithDifferentPayer());

        CreditCard creditCard = TestUtilsGenerator.getCreditCardUnlocked();
        Payment newPayment = TestUtilsGenerator.getNewPayment();

        Assertions.assertThatThrownBy(()
                        ->  paymentService.payCreditCard(creditCard.getCardNumber(),newPayment))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageContaining("Credit card does not exists");
    }

    @Test
    void payCreditCard_throwException_wheBuyerNotExists() {
        BDDMockito.when(creditCardRepo.findByCardNumber(ArgumentMatchers.anyString()))
                .thenReturn(TestUtilsGenerator.getCreditCardUnlocked());
        BDDMockito.when(paymentRepo.save(ArgumentMatchers.any(Payment.class)))
                .thenReturn(TestUtilsGenerator.getPaymentWithDifferentPayer());

        CreditCard creditCard = TestUtilsGenerator.getCreditCardUnlocked();
        Payment newPayment = TestUtilsGenerator.getNewPayment();

        Assertions.assertThatThrownBy(()
                        ->  paymentService.payCreditCard(creditCard.getCardNumber(),newPayment))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageContaining("Buyer does not exists");
    }

}