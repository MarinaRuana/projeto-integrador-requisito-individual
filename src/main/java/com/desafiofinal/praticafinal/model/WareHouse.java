package com.desafiofinal.praticafinal.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"manager", "sectorList"})

public class WareHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long wareHouseId;

    @OneToOne (cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_manager")
    private Manager manager;

    @OneToMany(mappedBy = "wareHouse", cascade = CascadeType.REFRESH)
    private List<Sector> sectorList;

}
