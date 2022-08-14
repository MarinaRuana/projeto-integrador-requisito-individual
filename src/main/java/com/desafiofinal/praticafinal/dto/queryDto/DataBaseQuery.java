package com.desafiofinal.praticafinal.dto.queryDto;

import java.time.LocalDate;


public interface DataBaseQuery {

    Long getSector_id();
    Long getId_product();
    Long getBatch_id();
    Long getCurrent_quantity();
    LocalDate getDue_date();
    String getCategory();
}
