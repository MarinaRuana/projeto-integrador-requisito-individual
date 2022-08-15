package com.desafiofinal.praticafinal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "buyer")
    @JsonIgnoreProperties({"creditCards", "cartList"})
    private Buyer payer;

    @ManyToOne
    @JoinColumn(name = "credit_card")
    private CreditCard creditCard;

    private double value;
}
