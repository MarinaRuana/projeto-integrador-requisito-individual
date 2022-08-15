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
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_buyer")
    @JsonIgnoreProperties({"creditCards", "cartList"})
    private Buyer idBuyer;

    @Column(length = 6, unique = true, nullable = false)
    private String cardNumber;

    private double limitTotal;

    private double limitAvailable;

    private boolean status;

    @OneToMany(mappedBy = "creditCard", cascade = CascadeType.REFRESH)
    @JsonIgnoreProperties({"listPurchase", "creditCard", "buyer"})
    private List<Cart> cartList;

    @OneToMany(mappedBy = "creditCard")
    @JsonIgnoreProperties({"creditCard", "payer"})
    private List<Payment> payments;

}
