package com.desafiofinal.praticafinal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"productList"})
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sellerName;

    @OneToMany(mappedBy ="seller")
    @JsonIgnore
    private Set<Product> productList;

    public Seller(String sellerName) {
        this.sellerName = sellerName;
    }
}
