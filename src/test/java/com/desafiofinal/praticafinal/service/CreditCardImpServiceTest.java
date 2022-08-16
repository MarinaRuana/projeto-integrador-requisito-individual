package com.desafiofinal.praticafinal.service;

import com.desafiofinal.praticafinal.exception.ElementAlreadyExistsException;
import com.desafiofinal.praticafinal.exception.ElementNotFoundException;
import com.desafiofinal.praticafinal.exception.ExceededCapacityException;
import com.desafiofinal.praticafinal.model.Buyer;
import com.desafiofinal.praticafinal.model.Cart;
import com.desafiofinal.praticafinal.model.CreditCard;
import com.desafiofinal.praticafinal.repository.BuyerRepo;
import com.desafiofinal.praticafinal.repository.CartRepo;
import com.desafiofinal.praticafinal.repository.CreditCardRepo;
import com.desafiofinal.praticafinal.utils.TestUtilsGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CreditCardImpServiceTest {

    @InjectMocks
    CreditCardImpService creditCardService;

    @Mock
    CreditCardRepo creditCardRepo;

    @Mock
    BuyerRepo buyerRepo;

    @Mock
    CartRepo cartRepo;

    @Test
    void registerCard_wheBuyerExists() {
        BDDMockito.when(buyerRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getBuyer()));
        BDDMockito.when(creditCardRepo.save(ArgumentMatchers.any(CreditCard.class)))
                .thenReturn(TestUtilsGenerator.getCreditCardUnlocked());

        CreditCard newCreditCard = TestUtilsGenerator.getNewCreditCardUnlocked();
        CreditCard savedCreditCard = creditCardService.registerCard(newCreditCard);

        assertThat(savedCreditCard).isNotNull();
        assertThat(savedCreditCard.getCardNumber()).isEqualTo(newCreditCard.getCardNumber());
    }

    @Test
    void registerCard_throwException_whenLimitExceeds() {
        BDDMockito.when(buyerRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getBuyer()));
        BDDMockito.when(creditCardRepo.save(ArgumentMatchers.any(CreditCard.class)))
                .thenReturn(TestUtilsGenerator.getCreditCardLimitsExceed());

        CreditCard newCreditCard = TestUtilsGenerator.getNewCreditCardLimitsExceed();

        assertThatThrownBy(()
                        ->  creditCardService.registerCard(newCreditCard))
                .isInstanceOf(ExceededCapacityException.class)
                .hasMessageContaining("Total limit cannot be less than the available limit");
    }

    @Test
    void registerCard_throwException_whenCardAlreadyExists() {
        BDDMockito.when(buyerRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getBuyer()));
        BDDMockito.when(creditCardRepo.findByCardNumber(ArgumentMatchers.anyString()))
                .thenReturn(TestUtilsGenerator.getCreditCardUnlocked());
        BDDMockito.when(creditCardRepo.save(ArgumentMatchers.any(CreditCard.class)))
                .thenReturn(TestUtilsGenerator.getCreditCardUnlocked());

        CreditCard newCreditCard = TestUtilsGenerator.getCreditCardUnlocked();

        assertThatThrownBy(()
                        ->  creditCardService.registerCard(newCreditCard))
                .isInstanceOf(ElementAlreadyExistsException.class)
                .hasMessageContaining("Credit card already exists");
    }

    @Test
    void registerCard_throwException_whenBuyerNotExits() {
        BDDMockito.when(creditCardRepo.findByCardNumber(ArgumentMatchers.anyString()))
                .thenReturn(null);
        BDDMockito.when(creditCardRepo.save(ArgumentMatchers.any(CreditCard.class)))
                .thenReturn(TestUtilsGenerator.getCreditCardUnlocked());

        CreditCard newCreditCard = TestUtilsGenerator.getNewCreditCardUnlocked();

        assertThatThrownBy(()
                        ->  creditCardService.registerCard(newCreditCard))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageContaining("Buyer does not exists");
    }

    @Test
    void getCreditCardLimits_wheCreditCardExists() {
        BDDMockito.when(creditCardRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getCreditCardUnlocked()));

        CreditCard creditCard = TestUtilsGenerator.getCreditCardUnlocked();

        String response = creditCardService.getCreditCardLimits(creditCard.getId());

        assertThat(response).contains("Card Number");
        assertThat(response).isNotEmpty();
        assertThat(response).isInstanceOf(String.class);
        assertThat(response).containsIgnoringCase("Limit Available");
        Mockito.verify(creditCardRepo, Mockito.atLeastOnce()).findById(creditCard.getId());
    }

    @Test
    void getCreditCardLimits_throwException_wheCreditCardNotExists() {

        CreditCard creditCard = TestUtilsGenerator.getNewCreditCardUnlocked();

        Assertions.assertThatThrownBy(()
                        -> creditCardService.getCreditCardLimits(creditCard.getId()))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageContaining("Credit card does not exists");
    }

    @Test
    void buyCart_whenCartAndCardExists() {
        BDDMockito.when(cartRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getCartOpen()));
        BDDMockito.when(creditCardRepo.findByCardNumber(ArgumentMatchers.anyString()))
                .thenReturn(TestUtilsGenerator.getCreditCardUnlocked());
        BDDMockito.when(creditCardRepo.save(ArgumentMatchers.any(CreditCard.class)))
                .thenReturn(TestUtilsGenerator.getCreditCardUnlocked());
        BDDMockito.when(cartRepo.save(ArgumentMatchers.any(Cart.class)))
                .thenReturn(TestUtilsGenerator.getCartFinished());

        CreditCard creditCard = TestUtilsGenerator.getCreditCardUnlocked();
        Cart cart = TestUtilsGenerator.getCartOpen();

        String response = creditCardService.buyCart(cart.getCartId(), creditCard.getCardNumber());

        assertThat(response).contains("your purchase value of:");
        assertThat(response).isNotEmpty();
        assertThat(response).isInstanceOf(String.class);
        assertThat(response).containsIgnoringCase("has been processed successfully");
        Mockito.verify(creditCardRepo, Mockito.atLeastOnce()).findByCardNumber(creditCard.getCardNumber());
    }

    @Test
    void buyCart_throwException_whenCreditCardNotExists() {
        BDDMockito.when(cartRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getCartOpen()));

        CreditCard creditCard = TestUtilsGenerator.getCreditCardUnlocked();
        Cart cart = TestUtilsGenerator.getCartOpen();

        Assertions.assertThatThrownBy(()
                        ->  creditCardService.buyCart(cart.getCartId(), creditCard.getCardNumber()))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageContaining("Credit card does not exists");
    }

    @Test
    void buyCart_throwException_whenCartNotExists() {
        BDDMockito.when(creditCardRepo.findByCardNumber(ArgumentMatchers.anyString()))
                .thenReturn(TestUtilsGenerator.getCreditCardUnlocked());

        CreditCard creditCard = TestUtilsGenerator.getCreditCardUnlocked();
        Cart cart = TestUtilsGenerator.getCartOpen();

        Assertions.assertThatThrownBy(()
                        ->  creditCardService.buyCart(cart.getCartId(), creditCard.getCardNumber()))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageContaining("Cart does not exists");
    }

    @Test
    void buyCart_throwException_whenCartIsFinished() {
        BDDMockito.when(cartRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getCartFinished()));
        BDDMockito.when(creditCardRepo.findByCardNumber(ArgumentMatchers.anyString()))
                .thenReturn(TestUtilsGenerator.getCreditCardUnlocked());

        CreditCard creditCard = TestUtilsGenerator.getCreditCardUnlocked();
        Cart cart = TestUtilsGenerator.getCartFinished();

        Assertions.assertThatThrownBy(()
                        ->  creditCardService.buyCart(cart.getCartId(), creditCard.getCardNumber()))
                .isInstanceOf(ElementAlreadyExistsException.class)
                .hasMessageContaining("This cart already finished");
    }

    @Test
    void buyCart_throwException_whenCartValueExceedsLimits() {
        BDDMockito.when(cartRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getCartOpenValueExceedLimits()));
        BDDMockito.when(creditCardRepo.findByCardNumber(ArgumentMatchers.anyString()))
                .thenReturn(TestUtilsGenerator.getCreditCardUnlocked());

        CreditCard creditCard = TestUtilsGenerator.getCreditCardUnlocked();
        Cart cart = TestUtilsGenerator.getCartOpenValueExceedLimits();

        Assertions.assertThatThrownBy(()
                        ->  creditCardService.buyCart(cart.getCartId(), creditCard.getCardNumber()))
                .isInstanceOf(ExceededCapacityException.class)
                .hasMessageContaining("Purchase value exceeds the card's current available limit");
    }

    @Test
    void buyCart_throwException_whenCreditCardIsLocked() {
        BDDMockito.when(cartRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getCartOpen()));
        BDDMockito.when(creditCardRepo.findByCardNumber(ArgumentMatchers.anyString()))
                .thenReturn(TestUtilsGenerator.getNewCreditCardLocked());

        CreditCard creditCard = TestUtilsGenerator.getNewCreditCardLocked();
        Cart cart = TestUtilsGenerator.getCartOpenValueExceedLimits();

        Assertions.assertThatThrownBy(()
                        ->  creditCardService.buyCart(cart.getCartId(), creditCard.getCardNumber()))
                .isInstanceOf(ExceededCapacityException.class)
                .hasMessageContaining("This credit card is blocked");
    }

    @Test
    void getCardBill_wheCreditCardExists() {
        BDDMockito.when(creditCardRepo.findByCardNumber(ArgumentMatchers.anyString()))
                .thenReturn(TestUtilsGenerator.getCreditCardWithCartList());

        CreditCard creditCard = TestUtilsGenerator.getCreditCardWithCartList();

        List<Cart> response = creditCardService.getCardBill(creditCard.getCardNumber());

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.size()).isEqualTo(1);
        Assertions.assertThat(response.get(0).getCartId()).isEqualTo(creditCard.getCartList().get(0).getCartId());
        Mockito.verify(creditCardRepo, Mockito.atLeastOnce()).findByCardNumber(creditCard.getCardNumber());

    }

    @Test
    void getCardBill_throwException_wheCreditCardNotExists() {
        CreditCard creditCard = TestUtilsGenerator.getCreditCardUnlocked();

        Assertions.assertThatThrownBy(()
                        ->  creditCardService.getCardBill(creditCard.getCardNumber()))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageContaining("Credit card does not exists");
    }

    @Test
    void updateCardStatus_WhenCardIsLocked() {
        BDDMockito.when(creditCardRepo.findByCardNumber(ArgumentMatchers.anyString()))
                .thenReturn(TestUtilsGenerator.getCreditCardLocked());
        BDDMockito.when(creditCardRepo.save(ArgumentMatchers.any()))
                .thenReturn(TestUtilsGenerator.getCreditCardUnlocked());

        CreditCard creditCard = TestUtilsGenerator.getNewCreditCardLocked();

        String response = creditCardService.updateCardStatus(creditCard.getCardNumber());

        assertThat(response).contains(creditCard.getCardNumber());
        assertThat(response).isNotEmpty();
        assertThat(response).isInstanceOf(String.class);
        assertThat(response).containsIgnoringCase("was unlocked, now you can use it");
        Mockito.verify(creditCardRepo, Mockito.atLeastOnce()).findByCardNumber(creditCard.getCardNumber());
    }

    @Test
    void updateCardStatus_WhenCardIsUnlocked() {
        BDDMockito.when(creditCardRepo.findByCardNumber(ArgumentMatchers.anyString()))
                .thenReturn(TestUtilsGenerator.getCreditCardUnlocked());
        BDDMockito.when(creditCardRepo.save(ArgumentMatchers.any()))
                .thenReturn(TestUtilsGenerator.getCreditCardLocked());

        CreditCard creditCard = TestUtilsGenerator.getCreditCardUnlocked();

        String response = creditCardService.updateCardStatus(creditCard.getCardNumber());

        assertThat(response).contains(creditCard.getCardNumber());
        assertThat(response).isNotEmpty();
        assertThat(response).isInstanceOf(String.class);
        assertThat(response).containsIgnoringCase("was locked, you cannot use it");
        Mockito.verify(creditCardRepo, Mockito.atLeastOnce()).findByCardNumber(creditCard.getCardNumber());
    }

    @Test
    void updateCardStatus_throwException_WhenCardNotExists() {
        CreditCard creditCard = TestUtilsGenerator.getCreditCardUnlocked();

        Assertions.assertThatThrownBy(()
                        ->  creditCardService.updateCardStatus(creditCard.getCardNumber()))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageContaining("Credit card does not exists");
    }

    @Test
    void getWallet_whenBuyerExists() {
        BDDMockito.when(buyerRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getBuyerWithWallet()));

        Buyer buyer = TestUtilsGenerator.getBuyerWithWallet();

        List<CreditCard> response = creditCardService.getWallet(buyer.getBuyerId());

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.size()).isEqualTo(1);
        Assertions.assertThat(response.get(0).getCardNumber()).isEqualTo(buyer.getCreditCards().get(0).getCardNumber());
        Mockito.verify(buyerRepo, Mockito.atLeastOnce()).findById(buyer.getBuyerId());
    }

    @Test
    void getWallet_throwException_whenBuyerNotExists() {
        Buyer buyer = TestUtilsGenerator.getBuyerWithWallet();

        Assertions.assertThatThrownBy(()
                        ->  creditCardService.getWallet(buyer.getBuyerId()))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageContaining("Buyer does not exists");
    }
}