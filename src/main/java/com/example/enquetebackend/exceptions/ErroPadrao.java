package com.example.enquetebackend.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class ErroPadrao extends RuntimeException{
    private HttpStatus statusCode;

    public ErroPadrao(String mensagem, HttpStatus statusCode){
        super(mensagem);
        this.statusCode = statusCode;
    }

    public Object messageError(){
        return Map.of("mensagem", getMessage());
    }

    public HttpStatus getStatusCode(){
        return this.statusCode;
    }
}