package com.desafiofinal.praticafinal.repository;

import com.desafiofinal.praticafinal.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
}
