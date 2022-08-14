package com.desafiofinal.praticafinal.service;

import com.desafiofinal.praticafinal.exception.ElementAlreadyExistsException;
import com.desafiofinal.praticafinal.exception.ElementNotFoundException;
import com.desafiofinal.praticafinal.exception.ExceededCapacityException;
import com.desafiofinal.praticafinal.model.BatchStock;
import com.desafiofinal.praticafinal.model.Cart;
import com.desafiofinal.praticafinal.repository.*;
import com.desafiofinal.praticafinal.utils.TestUtilsGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CartImpServiceTest {

    @InjectMocks
    CartImpService cartService;

    @Mock
    CartRepo cartRepo;

    @Mock
    IBatchStockRepo batchStockRepo;

    @Mock
    BuyerRepo buyerRepo;

    @BeforeEach
    public void setup(){}

    @Test
    void createPurchase_whenBuyerAndBatchStockExists() {
        BDDMockito.when(cartRepo.findById(ArgumentMatchers.anyLong()))
                        .thenReturn(Optional.ofNullable(TestUtilsGenerator.getCartFinished()));
        BDDMockito.when(buyerRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getBuyer()));
        BDDMockito.when(batchStockRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getBatchStockAndSectorWithCapacity()));

        Cart newCart = TestUtilsGenerator.getNewCartOpen();
        Double totalPrice = cartService.createPurchase(newCart);

        assertThat(totalPrice).isPositive();
        assertThat(totalPrice).isNotNull();
        assertThat(totalPrice).isEqualTo(1);
    }

    @Test
    void createPurchase_whenBuyerNotExists() {
        BDDMockito.when(cartRepo.findById(ArgumentMatchers.anyLong()))
                        .thenReturn(Optional.ofNullable(TestUtilsGenerator.getCartFinished()));
        BDDMockito.when(batchStockRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getBatchStockAndSectorWithCapacity()));

        Cart newCart = TestUtilsGenerator.getNewCartOpen();

        Assertions.assertThatThrownBy(()
                        -> cartService.createPurchase(newCart))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageContaining("Buyer does not exists");
    }

    @Test
    void createPurchase_whenBatchStockNotExists() {
        BDDMockito.when(cartRepo.findById(ArgumentMatchers.anyLong()))
                        .thenReturn(Optional.ofNullable(TestUtilsGenerator.getCartFinished()));
        BDDMockito.when(buyerRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getBuyer()));

        Cart newCart = TestUtilsGenerator.getNewCartOpen();

        Assertions.assertThatThrownBy(()
                        -> cartService.createPurchase(newCart))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageContaining("Batch stock does not exist");
    }

    @Test
    void getProducts_whenCartExists(){
        BDDMockito.when(cartRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getCartOpen()));

        Cart foundCart = TestUtilsGenerator.getCartOpen();
        List<BatchStock> response = cartService.getProducts(foundCart.getCartId());

        assertThat(response).isNotNull();
        verify(cartRepo, atLeastOnce()).findById(foundCart.getCartId());
    }

    @Test
    void getProducts_throwException_whenCartDoesNotExists(){
        Cart foundCart = TestUtilsGenerator.getNewCartOpen();
        Assertions.assertThatThrownBy(()
                        -> cartService.getProducts(foundCart.getCartId()))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageContaining("Cart does not exists");
    }

    @Test
    void updateStatus_wheCartExistsAndOpen() {
        BDDMockito.when(cartRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getCartOpen()));

        Cart foundCart = TestUtilsGenerator.getCartOpen();
        String finishedCart = cartService.updateStatus(foundCart.getCartId());

        assertThat(finishedCart).isEqualTo("Order completed successfully");
    }

    @Test
    void updateStatus_throwException_whenCartDoesNotExists(){
        Cart foundCart = TestUtilsGenerator.getNewCartOpen();
        Assertions.assertThatThrownBy(()
                        -> cartService.updateStatus(foundCart.getCartId()))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageContaining("Cart does not exist");
    }

    @Test
    void updateStatus_throwException_whenCartWasFinished(){
        BDDMockito.when(cartRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getCartFinished()));

        Cart foundCart = TestUtilsGenerator.getCartFinished();
        Assertions.assertThatThrownBy(()
                        -> cartService.updateStatus(foundCart.getCartId()))
                .isInstanceOf(ElementAlreadyExistsException.class)
                .hasMessageContaining("Cart already finished");
    }

    @Test
    void updateStatus_throwException_whenQuantityUnavailable(){
        BDDMockito.when(cartRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getNewCartOpenWithQuantityUnavailable()));

        Cart foundCart = TestUtilsGenerator.getNewCartOpenWithQuantityUnavailable();
        Assertions.assertThatThrownBy(()
                        -> cartService.updateStatus(foundCart.getCartId()))
                .isInstanceOf(ExceededCapacityException.class)
                .hasMessageContaining("Quantity unavailable");
    }

    @Test
    void updateStatus_throwException_whenMaximumCapacityExceeded(){
        BDDMockito.when(cartRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getNewCartOpenWithoutCapacity()));

        Cart foundCart = TestUtilsGenerator.getNewCartOpenWithoutCapacity();
        Assertions.assertThatThrownBy(()
                        -> cartService.updateStatus(foundCart.getCartId()))
                .isInstanceOf(ExceededCapacityException.class)
                .hasMessageContaining("Maximum capacity has been exceeded");
    }

}