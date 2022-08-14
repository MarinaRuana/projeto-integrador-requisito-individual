package com.desafiofinal.praticafinal.handler;

import com.desafiofinal.praticafinal.exception.ElementNotFoundException;
import com.desafiofinal.praticafinal.exception.ElementAlreadyExistsException;
import com.desafiofinal.praticafinal.exception.ExceededCapacityException;
import com.desafiofinal.praticafinal.exception.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerException extends Throwable{

    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<ExceptionDetails> exceptionHandler(ElementNotFoundException ex){
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title(HttpStatus.NOT_FOUND.name())
                        .status(HttpStatus.NOT_FOUND.value())
                        .message(ex.getMessage())
                        .build(), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ElementAlreadyExistsException.class)
    public ResponseEntity<ExceptionDetails> exceptionHandler(ElementAlreadyExistsException ex){
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title(HttpStatus.CONFLICT.name())
                        .status(HttpStatus.CONFLICT.value())
                        .message(ex.getMessage())
                        .build(), HttpStatus.CONFLICT
        );
    }
    @ExceptionHandler(ExceededCapacityException.class)
    public ResponseEntity<ExceptionDetails> exceptionHandler(ExceededCapacityException ex){
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title(HttpStatus.METHOD_NOT_ALLOWED.name())
                        .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                        .message(ex.getMessage())
                        .build(), HttpStatus.METHOD_NOT_ALLOWED
        );
    }


}
