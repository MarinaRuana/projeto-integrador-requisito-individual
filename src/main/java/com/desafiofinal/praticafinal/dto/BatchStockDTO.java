package com.desafiofinal.praticafinal.dto;

import com.desafiofinal.praticafinal.dto.requestResponseDto.ProductResponseDTO;
import com.desafiofinal.praticafinal.model.BatchStock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchStockDTO {

    private long batchNumber;

    @NotNull(message = "Please enter a valid product")
    private ProductResponseDTO product;

    @NotNull(message = "Current temperature cannot be null")
    private Float currentTemperature;

    @NotNull(message = "Initial temperature cannot be null")
    private Float minimumTemperature;

    @NotNull(message = "Initial quantity cannot be null")
    @DecimalMin(value = "1", message = "Initial quantity cannot be less than 1")
    private Long initialQuantity;

    @NotNull(message = "Current quantity cannot be null")
    private Long currentQuantity;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @NotNull(message = "Manufacturing Date cannot be null. Format: yyyy/MM/dd")
    private LocalDate manufacturingDate;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @NotNull(message = "Manufacturing Date cannot be null. Format: yyyy/MM/dd")
    private LocalDate manufacturingTime;

    @Future(message = "Due date must be in the future")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @NotNull(message = "Due date cannot be null")
    private LocalDate dueDate;

    private Long inBoundOrderId;

    public BatchStockDTO(BatchStock batchStock) {
        this.batchNumber = batchStock.getBatchId();
        this.product = new ProductResponseDTO(batchStock.getProduct());
        this.currentTemperature = batchStock.getCurrentTemperature();
        this.minimumTemperature = batchStock.getMinimumTemperature();
        this.initialQuantity = batchStock.getInitialQuantity();
        this.currentQuantity = batchStock.getCurrentQuantity();
        this.manufacturingDate = batchStock.getManufacturingDate();
        this.manufacturingTime = batchStock.getManufacturingTime();
        this.dueDate = batchStock.getDueDate();
        this.inBoundOrderId = batchStock.getInBoundOrder().getOrderId();
    }

    public static BatchStock convertBatchStockDtoToBatchStock (BatchStockDTO batchStockDto){
        return BatchStock.builder()
                .batchId(batchStockDto.getBatchNumber())
                .product(ProductResponseDTO.convertToProductResponseDTO(batchStockDto.getProduct()))
                .currentTemperature(batchStockDto.getCurrentTemperature())
                .minimumTemperature(batchStockDto.getMinimumTemperature())
                .initialQuantity(batchStockDto.getInitialQuantity())
                .currentQuantity(batchStockDto.getCurrentQuantity())
                .manufacturingDate(batchStockDto.getManufacturingDate())
                .manufacturingTime(batchStockDto.getManufacturingTime())
                .dueDate(batchStockDto.getDueDate())
                .build();
    }
    public static BatchStock convertBatchStockDtoToBatchStockIdOnly (BatchStockDTO batchStockDto){
        return BatchStock.builder()
                .batchId(batchStockDto.getBatchNumber())
                .build();
    }


    public static List<BatchStock> convertToListEntity (List<BatchStockDTO> batchStockDtoList){
        List<BatchStock> batchStockList = new ArrayList<>();
        for(BatchStockDTO batchStockDto: batchStockDtoList){
            batchStockList.add(convertBatchStockDtoToBatchStock(batchStockDto));
        }
        return batchStockList;
    }

    public static List<BatchStockDTO> convertToListDto (List<BatchStock> batchStockList){
        List<BatchStockDTO> batchStockListDto = new ArrayList<>();
        for(BatchStock batchStock: batchStockList){
            batchStockListDto.add(new BatchStockDTO(batchStock));
        }
        return batchStockListDto;
    }
}
