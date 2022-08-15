package com.desafiofinal.praticafinal.service;

import com.desafiofinal.praticafinal.model.Cart;
import com.desafiofinal.praticafinal.model.CreditCard;

import java.util.List;

public interface ICreditCardService {

    CreditCard registerCard(CreditCard newCreditCard);

    String getCreditCardLimits(Long id);

    String buyCart(Long cartId, Long cardNumber);

    List<Cart> getCardBill(Long cardNumber);

    String updateCardStatus(Long cardNumber);

    List<CreditCard> getWallet(long buyerId);
}
