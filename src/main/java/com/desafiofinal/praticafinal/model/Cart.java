package com.desafiofinal.praticafinal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn (name = "buyer_id")
    @JsonIgnoreProperties({"cartList", "creditCards"})
    private Buyer buyer;

    private double totalPrice;

    private LocalDate date;

    private String orderStatus;

    @OneToMany (mappedBy = "idCart")
    @JsonIgnoreProperties({"idCart", "batchStock", "listPurchase"})
    private List<Purchase> listPurchase;

    @ManyToOne
    @JoinColumn(name = "credit_card_id")
    @JsonIgnoreProperties({"cartList", "idBuyer"})
    private CreditCard creditCard;
}
