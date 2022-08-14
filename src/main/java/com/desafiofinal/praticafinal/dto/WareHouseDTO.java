package com.desafiofinal.praticafinal.dto;

import com.desafiofinal.praticafinal.model.Sector;
import com.desafiofinal.praticafinal.model.WareHouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WareHouseDTO {

    private long wareHouseId;
    private List<Sector> sectorList;

    public WareHouseDTO(WareHouse wareHouse) {
        this.wareHouseId = wareHouse.getWareHouseId();
        this.sectorList = wareHouse.getSectorList();
    }

    public static WareHouse convertToWareHouse(WareHouseDTO wareHouseDTO){
        return WareHouse.builder()
                .wareHouseId(wareHouseDTO.getWareHouseId())
                .build();
    }

}