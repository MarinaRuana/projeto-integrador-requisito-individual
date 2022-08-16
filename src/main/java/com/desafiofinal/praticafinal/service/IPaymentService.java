package com.desafiofinal.praticafinal.service;

import com.desafiofinal.praticafinal.model.Payment;

public interface IPaymentService {

    String payCreditCard(String cardNumber, Payment payment);

}
