package com.desafiofinal.praticafinal.dto.queryDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
public class DataBaseStockQueryImp implements DataBaseStockQuery{

    private Long batchId;
    private Long productId;
    private String productType;
    private LocalDate dueDate;
    private Long currentQuantity;
    private Long sectorId;

    public DataBaseStockQueryImp(Long batchId, Long productId, String productType, LocalDate dueDate, Long currentQuantity, Long sectorId) {
        this.batchId = this.getBatch_id();
        this.productId = this.getId_product();
        this.productType = this.getProduct_type();
        this.dueDate = this.getDue_date();
        this.currentQuantity = this.getCurrent_quantity();
        this.sectorId = this.getSector_id();
    }

    @Override
    public Long getBatch_id() {
        return this.batchId;
    }

    @Override
    public Long getId_product() {
        return this.productId;
    }

    @Override
    public String getProduct_type() {
        return this.productType;
    }

    @Override
    public LocalDate getDue_date() {
        return this.dueDate;
    }

    @Override
    public Long getCurrent_quantity() {
        return this.currentQuantity;
    }

    @Override
    public Long getSector_id() {
        return this.sectorId;
    }
}