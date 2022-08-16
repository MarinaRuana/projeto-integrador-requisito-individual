package com.desafiofinal.praticafinal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long buyerId;

    private String buyerName;

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.REFRESH)
    @JsonIgnoreProperties({"listPurchase", "buyer", "creditCard"})
    private List<Cart> cartList;

    @OneToMany(mappedBy = "idBuyer", cascade = CascadeType.REFRESH)
    @JsonIgnoreProperties({"creditCards", "idBuyer", "cartList"})
    private List<CreditCard> creditCards;

    @OneToMany(mappedBy = "payer")
    @JsonIgnoreProperties({"payer", "creditCard"})
    private List<Payment> payments;
}
