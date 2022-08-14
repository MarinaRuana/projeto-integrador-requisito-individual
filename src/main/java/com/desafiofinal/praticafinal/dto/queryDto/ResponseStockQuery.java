package com.desafiofinal.praticafinal.dto.queryDto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ResponseStockQuery {
    private Long batchId;
    private Long idProduct;
    private String productType;
    private LocalDate dueDate;
    private Long currentQuantity;
}
