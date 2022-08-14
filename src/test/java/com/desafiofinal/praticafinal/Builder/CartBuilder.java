package com.desafiofinal.praticafinal.Builder;

import java.time.LocalDate;

import com.desafiofinal.praticafinal.model.Cart;

public class CartBuilder {
    
    private Cart cart;

    private CartBuilder() {}

    public static CartBuilder aCartWhithoutBuyer() {

      CartBuilder builder = new CartBuilder();
      
      builder.cart = new Cart();
      builder.cart.setBuyer(BuyerBuilder.aBuyerWithoutCart().create());
      builder.cart.setCartId(1l);
      builder.cart.setDate(LocalDate.now());
      builder.cart.setListPurchase(PurchaseBatchStockBuilder.aListOfPurchaseBatchStocks());
      builder.cart.setOrderStatus("Concluído");
      builder.cart.setTotalPrice(50.00);

      return builder;
    }

    public Cart create() {
        return cart;
    }
}