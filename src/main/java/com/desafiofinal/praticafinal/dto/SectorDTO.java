package com.desafiofinal.praticafinal.dto;

import com.desafiofinal.praticafinal.model.InBoundOrder;
import com.desafiofinal.praticafinal.model.Sector;
import com.desafiofinal.praticafinal.model.WareHouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectorDTO {

    private long sectorId;
    private String category;
    private double capacity;
    private List<InBoundOrderDTO> orderList;
    private WareHouseDTO wareHouse;

    public SectorDTO(Sector sector) {
        this.sectorId = sector.getSectorId();
        this.category = sector.getCategory();
        this.capacity = sector.getCapacity();
        this.orderList = InBoundOrderDTO.convertListToDTO(sector.getOrderList());
        this.wareHouse = new WareHouseDTO(sector.getWareHouse());
    }

    public static Sector convertToSector(SectorDTO sectorDTO){
        return Sector.builder()
                .sectorId(sectorDTO.sectorId)
                .build();
    }
}
