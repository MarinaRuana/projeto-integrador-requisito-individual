package com.desafiofinal.praticafinal.model;

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
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long managerId;
    private String managerName;

    @OneToOne (mappedBy = "manager", cascade = CascadeType.REFRESH)
    private WareHouse wareHouse;
}
