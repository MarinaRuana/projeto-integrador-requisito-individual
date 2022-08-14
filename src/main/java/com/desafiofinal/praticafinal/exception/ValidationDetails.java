package com.desafiofinal.praticafinal.exception;

import lombok.*;

import java.util.List;

@Builder
@Getter @Setter
@AllArgsConstructor
public class ValidationDetails {
    private final String message;
    private final int code;
    private String status;
    private final String objectName;
    private final List<ErrorObject> errors;
}
