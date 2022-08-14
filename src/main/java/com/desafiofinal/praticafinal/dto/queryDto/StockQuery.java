package com.desafiofinal.praticafinal.dto.queryDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockQuery {

    private long batchId;

    private long currentQuantity;

    @JsonIgnore
    private long sectorId;

    private LocalDate dueDate;

    @JsonIgnore
    private long productId;


}
