package com.example.enquetebackend.exceptions;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ErroPersonalizadoHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ErroPadrao.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ErroPadrao ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.messageError());
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Object> handleTokenVerificationException(TokenExpiredException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("mensagem", "ex.getMessage()"));
    }

    /*@ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity<Object> handleTokenDecodeException(JWTDecodeException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Object> handleTokenExpiredException(TokenExpiredException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("mensagem", "Sessão expirada."));
    }*/
}