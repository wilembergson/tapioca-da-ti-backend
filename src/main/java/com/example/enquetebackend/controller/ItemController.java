package com.example.enquetebackend.controller;

import com.example.enquetebackend.dto.AtualizarItemDTO;
import com.example.enquetebackend.dto.NovoItemDTO;
import com.example.enquetebackend.dto.VotoDTO;
import com.example.enquetebackend.entity.Item;
import com.example.enquetebackend.entity.Voto;
import com.example.enquetebackend.service.ItemService;
import com.example.enquetebackend.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/item")
public class ItemController {
    private ItemService service;

    @Autowired
    public ItemController(ItemService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> novaResposta(@RequestBody NovoItemDTO dto){
        this.service.novoItem(dto);
        return new ResponseEntity<>(Map.of("mensagem", "Item adicionado."), HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Object> atualizar(@RequestBody AtualizarItemDTO dto){
        this.service.atualizarItem(dto);
        return new ResponseEntity<>(Map.of("mensagem", "Item atualizado."), HttpStatus.OK);
    }

    @PutMapping("/atualizar-status/{id}")
    public ResponseEntity<Object> mudarStatusPagamento(@PathVariable String id){
        service.mudarStatusPagamento(Integer.parseInt(id));
        return ResponseEntity.ok(Map.of("mensagem", "Status atualizado."));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Item>> listar(){
        return ResponseEntity.ok(service.listarItens());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> obterPorId(@PathVariable String id){
        Item item = service.obterPorId(Integer.parseInt(id));
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarPorId(@PathVariable String id){
        service.deletarPorId(Integer.parseInt(id));
        return ResponseEntity.ok(Map.of("mensagem", "Item deletado do pedido."));
    }
}
