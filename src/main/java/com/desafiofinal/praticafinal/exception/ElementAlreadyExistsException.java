package com.desafiofinal.praticafinal.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(code = HttpStatus.CONFLICT)
public class ElementAlreadyExistsException extends RuntimeException{
    public ElementAlreadyExistsException(String message){
        super(message);
    }
}
