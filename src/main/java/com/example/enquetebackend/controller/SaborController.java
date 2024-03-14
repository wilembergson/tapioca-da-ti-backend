package com.example.enquetebackend.controller;

import com.example.enquetebackend.dto.NovoItemDTO;
import com.example.enquetebackend.entity.Item;
import com.example.enquetebackend.entity.Sabor;
import com.example.enquetebackend.service.ItemService;
import com.example.enquetebackend.service.SaborService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sabor")
public class SaborController {
    private SaborService service;

    @Autowired
    public SaborController(SaborService service){
        this.service = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Sabor>> listar(){
        return ResponseEntity.ok(service.listarSabores());
    }
}
