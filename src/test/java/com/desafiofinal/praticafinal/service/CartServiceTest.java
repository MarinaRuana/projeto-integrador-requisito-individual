package com.desafiofinal.praticafinal.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.desafiofinal.praticafinal.model.Cart;

import com.desafiofinal.praticafinal.model.Purchase;
import com.desafiofinal.praticafinal.repository.BuyerRepo;

import com.desafiofinal.praticafinal.repository.CartRepo;

import com.desafiofinal.praticafinal.repository.IBatchStockRepo;
import com.desafiofinal.praticafinal.repository.PurchaseRepo;
import com.desafiofinal.praticafinal.Builder.BatchStockBuilder;
import com.desafiofinal.praticafinal.Builder.BuyerBuilder;
import com.desafiofinal.praticafinal.Builder.PurchaseBatchStockBuilder;
import com.desafiofinal.praticafinal.Builder.CartBuilder;
import org.mockito.junit.jupiter.MockitoExtension;;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    

    @InjectMocks
    private CartImpService cartService; 

    @Mock
    private BuyerRepo buyerRepo;

    @Mock
    private IBatchStockRepo batchStockRepo;

    @Mock
    private CartRepo cartRepo;

    @Mock
    private PurchaseRepo purchaseBatchStockRepo;

    

    @Test
    @DisplayName("Test if a purchase is correctly made")
    void createPurchaseTest() {
        Cart cart = CartBuilder.aCartWhithoutBuyer().create();
        List<Purchase> cartBatchStockList = PurchaseBatchStockBuilder.aListOfPurchaseBatchStocks();

       when(buyerRepo.findById(anyLong())).thenReturn(Optional.of(BuyerBuilder.aBuyerWithoutCart().create()));
       when(batchStockRepo.findById(anyLong())).thenReturn(Optional.of(BatchStockBuilder.aBatchStock().create()));
       when(cartRepo.save(ArgumentMatchers.any(Cart.class))).thenReturn(cart);
       when(purchaseBatchStockRepo.saveAll(cartBatchStockList)).thenReturn(cartBatchStockList);
       

       Double total = cartService.createPurchase(cart);

       assertThat(total).isNotNull();
       assertThat(total).isEqualTo(110);
        
    }

}