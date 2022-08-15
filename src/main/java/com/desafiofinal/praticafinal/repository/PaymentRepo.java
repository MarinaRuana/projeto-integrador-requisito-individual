package com.desafiofinal.praticafinal.repository;

import com.desafiofinal.praticafinal.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {
}
