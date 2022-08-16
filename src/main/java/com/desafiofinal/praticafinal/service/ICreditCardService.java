package com.desafiofinal.praticafinal.service;

import com.desafiofinal.praticafinal.model.Cart;
import com.desafiofinal.praticafinal.model.CreditCard;

import java.util.List;

public interface ICreditCardService {

    CreditCard registerCard(CreditCard newCreditCard);

    String getCreditCardLimits(Long id);

    String buyCart(Long cartId, String cardNumber);

    List<Cart> getCardBill(String cardNumber);

    String updateCardStatus(String cardNumber);

    List<CreditCard> getWallet(long buyerId);
}
