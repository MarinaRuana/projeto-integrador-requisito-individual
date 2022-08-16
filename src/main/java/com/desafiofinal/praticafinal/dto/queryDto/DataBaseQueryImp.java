package com.desafiofinal.praticafinal.dto.queryDto;

import lombok.*;

import javax.persistence.Basic;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
public class DataBaseQueryImp implements DataBaseQuery{



    private Long sector_id;
    private Long id_product;
    private Long batch_id;
    private Long current_quantity;
    private LocalDate due_date;
    private String category;

    public DataBaseQueryImp(Long sectorId, Long idProduct, Long batchId, Long currentQuantity, LocalDate dueDate, String category) {
        this.sector_id = getSector_id();
        this.id_product = getId_product();
        this.batch_id = getBatch_id();
        this.current_quantity = getCurrent_quantity();
        this.due_date = getDue_date();
        this.category = getCategory();
    }

    @Override
    public Long getSector_id() {
        return this.sector_id;
    }

    @Override
    public Long getId_product() {
        return this.id_product;
    }

    @Override
    public Long getBatch_id() {
        return this.batch_id;
    }

    @Override
    public Long getCurrent_quantity() {
        return this.current_quantity;
    }

    @Override
    public LocalDate getDue_date() {
        return this.due_date;
    }

    @Override
    public String getCategory() {
        return this.category;
    }
}
