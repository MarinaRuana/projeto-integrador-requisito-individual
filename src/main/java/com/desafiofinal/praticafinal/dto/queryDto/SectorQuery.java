package com.desafiofinal.praticafinal.dto.queryDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectorQuery {

    private long sectorId;

    private String category;

}
