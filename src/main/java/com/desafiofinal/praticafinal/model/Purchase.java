package com.desafiofinal.praticafinal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long purchaseId;

    @ManyToOne
    @JoinColumn(name = "id_cart")
    @JsonIgnoreProperties({"listPurchase", "idCart", "creditCard", "batchStock"})
    private Cart idCart;

    @ManyToOne
    @JoinColumn(name = "id_batchStock")
    private BatchStock batchStock;

    private double pricePerProduct;

    private int productQuantity;


}
