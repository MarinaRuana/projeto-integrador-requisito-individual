package com.desafiofinal.praticafinal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@ToString(exclude = {"wareHouse", "orderList"})

@NoArgsConstructor
@AllArgsConstructor
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sectorId;
    private String category;
    private double capacity;

    @OneToMany(mappedBy = "sector", cascade = CascadeType.REFRESH)
    @JsonIgnoreProperties("sector")
    private List<InBoundOrder> orderList;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_warehouse")
    @JsonIgnoreProperties("sectorList")
    private WareHouse wareHouse;

    private double maxCapacity;
}
