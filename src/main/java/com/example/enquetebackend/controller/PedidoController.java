package com.example.enquetebackend.controller;

import com.example.enquetebackend.dto.PedidoDTO;
import com.example.enquetebackend.entity.Pedido;
import com.example.enquetebackend.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    private PedidoService service;

    @Autowired
    public PedidoController(PedidoService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> novoPedido(@RequestBody PedidoDTO dto){
        this.service.novoPedido(dto);
        return new ResponseEntity<>(Map.of("mensagem", "Novo pedido criado."), HttpStatus.CREATED);
    }

    @PutMapping("/atualizar-pix")
    public ResponseEntity<Object> atualizarPix(@RequestBody PedidoDTO dto){
        this.service.atualizarPix(dto);
        return new ResponseEntity<>(Map.of("mensagem", "Pix atualizado."), HttpStatus.OK);
    }

    @PutMapping("/atualizar-status/{statusId}")
    public ResponseEntity<Object> atualizarStatus(@PathVariable String statusId){
        this.service.atualizarStatus(Integer.parseInt(statusId));
        return new ResponseEntity<>(Map.of("mensagem", "Status atualizado."), HttpStatus.OK);
    }

    @GetMapping("/atual")
    public ResponseEntity<Pedido> listar(){
        return ResponseEntity.ok(service.obterPedidoEmCriacao());
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Object> deletar(){
        service.deletar();
        return ResponseEntity.ok(Map.of("mensagem", "Pedido deletado."));
    }
}
