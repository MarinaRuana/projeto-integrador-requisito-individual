package com.desafiofinal.praticafinal.dto.requestResponseDto;

import com.desafiofinal.praticafinal.dto.BatchStockDTO;
import com.desafiofinal.praticafinal.model.InBoundOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class InBoundOrderResponseDTO {

    private List<BatchStockDTO> batchStock;

    public InBoundOrderResponseDTO(InBoundOrder inBoundOrder) {
         this.batchStock = BatchStockDTO.convertToListDto(inBoundOrder.getBatchStockList());
    }

}
