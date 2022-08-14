package com.desafiofinal.praticafinal.dto.queryDto;

import java.time.LocalDate;

public interface DataBaseStockQuery {
    Long getBatch_id();
    Long getId_product();
    String getProduct_type();
    LocalDate getDue_date();
    Long getCurrent_quantity();


}
