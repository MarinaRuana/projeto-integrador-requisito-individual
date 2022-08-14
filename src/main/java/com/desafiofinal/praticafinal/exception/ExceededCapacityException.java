package com.desafiofinal.praticafinal.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
public class ExceededCapacityException extends RuntimeException{

    public ExceededCapacityException(String message){
        super(message);
    }
}
