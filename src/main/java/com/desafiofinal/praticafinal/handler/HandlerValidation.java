package com.desafiofinal.praticafinal.handler;

import com.desafiofinal.praticafinal.exception.ErrorObject;
import com.desafiofinal.praticafinal.exception.ValidationDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class HandlerValidation extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
       List<ErrorObject> errors = getErrors(ex);
       ValidationDetails validationDetails = getValidationDetails(ex, status, errors);

        return new ResponseEntity<>(validationDetails, status);
    }

    private ValidationDetails getValidationDetails(MethodArgumentNotValidException ex, HttpStatus status, List<ErrorObject> errors) {
        return new ValidationDetails("This Request has invalid fields", status.value(),
                status.getReasonPhrase(), ex.getBindingResult().getObjectName(), errors);
    }

    private List<ErrorObject> getErrors(MethodArgumentNotValidException ex){
        return ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorObject(error.getDefaultMessage(), error.getField(), error.getRejectedValue()))
                .collect(Collectors.toList());

    }
}
