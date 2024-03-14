package com.example.enquetebackend.controller;

import com.example.enquetebackend.dto.LoginDTO;
import com.example.enquetebackend.dto.LoginResponseDTO;
import com.example.enquetebackend.dto.RegisterDTO;
import com.example.enquetebackend.entity.Usuario;
import com.example.enquetebackend.repository.UsuarioRepository;
import com.example.enquetebackend.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/autorizacao")
public class AutorizacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO loginDTO){
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.senha());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.gerarToken((Usuario) auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponseDTO(token));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(Map.of("mensagem", "Usuário ou senha incorretos."));
        }
    }

    @GetMapping("/usuario")
    public ResponseEntity obterUsuario(@RequestHeader(name = "Authorization") String authorization){
        var usuario = tokenService.validarToken(authorization.replace("Bearer ", ""));
        return ResponseEntity.ok().body(Map.of("usuario", usuario));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.usuarioRepository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        Usuario newUser = new Usuario(data.id(), data.login(), encryptedPassword);
        this.usuarioRepository.save(newUser);
        return ResponseEntity.ok().build();
    }
}
