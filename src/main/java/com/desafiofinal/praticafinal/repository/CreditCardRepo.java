package com.desafiofinal.praticafinal.repository;

import com.desafiofinal.praticafinal.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepo extends JpaRepository<CreditCard, Long> {
    CreditCard findByCardNumber(String cardNumber);
}
