package com.desafiofinal.praticafinal.dto.queryDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BatchStockSectorQuantityDTO {

    private long batchNumber;
    private long currentQuantity;
    private long productId;
    private long sector;

}
