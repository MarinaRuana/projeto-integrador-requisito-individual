package com.desafiofinal.praticafinal.dto.requestResponseDto;

import com.desafiofinal.praticafinal.model.BatchStock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchStockResponseDTO {

    private Long batchId;

    private Long currentQuantity;

    private LocalDate dueDate;

    private ProductResponseDTO productId;

    public BatchStockResponseDTO(BatchStock batchStock) {
        this.batchId = batchStock.getBatchId();
        this.currentQuantity = batchStock.getCurrentQuantity();
        this.dueDate = batchStock.getDueDate();
        this.productId = new ProductResponseDTO(batchStock.getProduct());
    }

    public static BatchStock convertBatchStockDtoToBatchStock (BatchStockResponseDTO batchStockDto){
        return BatchStock.builder()
                .batchId(batchStockDto.getBatchId())
                .currentQuantity(batchStockDto.getCurrentQuantity())
                .dueDate(batchStockDto.getDueDate())
                .product(ProductResponseDTO.convertToProductResponseDTO(batchStockDto.getProductId()))
                .build();
    }

    public static List<BatchStock> convertToListEntity (List<BatchStockResponseDTO> batchStockDtoList){
        List<BatchStock> batchStockList = new ArrayList<>();
        for(BatchStockResponseDTO batchStockDto: batchStockDtoList){
            batchStockList.add(convertBatchStockDtoToBatchStock(batchStockDto));
        }
        return batchStockList;
    }

    public static List<BatchStockResponseDTO> convertToListDto (List<BatchStock> batchStockList){
        List<BatchStockResponseDTO> batchStockListDto = new ArrayList<>();
        for(BatchStock batchStock: batchStockList){
            batchStockListDto.add(new BatchStockResponseDTO(batchStock));
        }
        return batchStockListDto;
    }
}
