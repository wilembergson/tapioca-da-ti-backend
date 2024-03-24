package com.example.enquetebackend.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErroPersonalizadoHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ErroPadrao.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ErroPadrao ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.messageError());
    }
}