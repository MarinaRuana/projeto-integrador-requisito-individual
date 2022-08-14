package com.desafiofinal.praticafinal.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public
class ExceptionDetails {

    private final String title;
    private final int status;
    private final String message;

}
