package com.desafiofinal.praticafinal.model;

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
    private long cartBatchStockId;

    @ManyToOne
    @JoinColumn(name = "id_cart")
    private Cart idCart;

    @ManyToOne
    @JoinColumn(name = "id_batchStock")
    private BatchStock batchStock;

    private double pricePerProduct;

    private int productQuantity;


}
