package com.example.enquetebackend.controller;

import com.example.enquetebackend.dto.VotoDTO;
import com.example.enquetebackend.entity.Voto;
import com.example.enquetebackend.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/voto")
public class VotoController {
    private VotoService service;

    @Autowired
    public VotoController(VotoService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> novaResposta(@RequestBody VotoDTO respostaDTO){
        this.service.novoVoto(respostaDTO);
        return new ResponseEntity<>(Map.of("mensagem", "Novo voto adicionado."), HttpStatus.CREATED);
    }

    @GetMapping("/listar-por-enquete/{enquete_id}")
    public ResponseEntity<List<Voto>> listarRespostasPorEnqueteId(@PathVariable  Integer enquete_id){
        return new ResponseEntity<>(service.votosPorEnqueteId(enquete_id), HttpStatus.OK);
    }
}
