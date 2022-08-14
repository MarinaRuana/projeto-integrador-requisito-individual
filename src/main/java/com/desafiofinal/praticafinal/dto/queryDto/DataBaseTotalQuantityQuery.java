package com.desafiofinal.praticafinal.dto.queryDto;

import java.time.LocalDate;


public interface DataBaseTotalQuantityQuery {

    Long getSector_id();
    Long getId_product();
   // Long getBatch_id();
    Long getTotal_quantity();
    String getCategory();
}
