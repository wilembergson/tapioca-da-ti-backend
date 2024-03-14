package com.example.enquetebackend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.enquetebackend.entity.Usuario;
import com.example.enquetebackend.exceptions.ErroPadrao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("autorizacao-api")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(tempoExpiracao())
                    .sign(algorithm);
            return token;
        }catch (JWTCreationException exception){
            throw new RuntimeException("Erro na geração do token.", exception);
        }
    }

    public String validarToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("autorizacao-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (TokenExpiredException e){
            throw new ErroPadrao("Sessão expirada.", HttpStatus.FORBIDDEN);
        }catch (JWTDecodeException e){
            throw new ErroPadrao("Token inválido.", HttpStatus.FORBIDDEN);
        }
    }

    private Instant tempoExpiracao(){
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
