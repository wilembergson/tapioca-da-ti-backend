package com.example.enquetebackend.controller;

import com.example.enquetebackend.dto.EnqueteDTO;
import com.example.enquetebackend.dto.AtualizarResultadoStatusDTO;
import com.example.enquetebackend.dto.ResultadoDTO;
import com.example.enquetebackend.entity.Enquete;
import com.example.enquetebackend.service.EnqueteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/enquete")
public class EnqueteController {

    private EnqueteService service;

    @Autowired
    public EnqueteController(EnqueteService service){
        this.service = service;
    }

    @GetMapping("/encerradas")
    public ResponseEntity<List<Enquete>> listarEncerradas(){
        return new ResponseEntity<>(service.listarEnquetesEncerradas(), HttpStatus.OK);
    }

    @GetMapping("/ativa")
    public ResponseEntity<Enquete> ativa(){
        Enquete enquete = service.obterEnqueteAtiva();
        return  ResponseEntity.ok(enquete);
    }

    @GetMapping("/obter-resultado")
    public ResponseEntity<ResultadoDTO> obterResultado(){
        ResultadoDTO enquete = service.obterResultadoEnquete();
        if (enquete == null) {
            return null;
        }
        return  ResponseEntity.ok(enquete);
    }

    @GetMapping("/obter-resultado/{id}")
    public ResponseEntity<ResultadoDTO> obterResultadoPorId(@PathVariable Integer id){
        ResultadoDTO enquete = service.obterResultadoEnquetePorId(id);
        if (enquete == null) {
            return null;
        }
        return  ResponseEntity.ok(enquete);
    }

    @PutMapping("/atualizar-resultado-status/{id}")
    public void ativarResultado(@PathVariable Integer id, @RequestBody AtualizarResultadoStatusDTO exibirResultadoDTO){
        service.mudarStatusResultadoEnquete(id, exibirResultadoDTO.getStatus());
    }

    @PutMapping("/encerrar")
    public ResponseEntity<Object> encerrar(){
        service.encerrarEnquete();
        return new ResponseEntity<>(Map.of("mensagem", "Enquete encerrada."), HttpStatus.OK);
    }

    @PutMapping("/atualizar/{tempo}")
    public ResponseEntity<Object> atualizar(@PathVariable Integer tempo){
        service.atualizarrEnquete(tempo);
        return new ResponseEntity<>(Map.of("mensagem", "Tempo ajustado com sucesso."), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody EnqueteDTO enqueteDTO){
        this.service.novaEnquete(enqueteDTO);
        return new ResponseEntity<>(Map.of("mensagem", "Nova enquete criada."), HttpStatus.CREATED);
    }
}
