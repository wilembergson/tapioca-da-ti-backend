package com.example.enquetebackend.service;

import com.example.enquetebackend.dto.NovoItemDTO;
import com.example.enquetebackend.entity.Item;
import com.example.enquetebackend.entity.Pedido;
import com.example.enquetebackend.entity.Sabor;
import com.example.enquetebackend.exceptions.ErroPadrao;
import com.example.enquetebackend.repository.ItemRepository;
import com.example.enquetebackend.repository.PedidoRepository;
import com.example.enquetebackend.repository.SaborRepository;
import com.example.enquetebackend.util.PedidoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaborService {

    private final SaborRepository saborRepository;


    @Autowired
    public SaborService(SaborRepository saborRepository){
        this.saborRepository = saborRepository;
    }

    public List<Sabor> listarSabores(){
        return saborRepository.findAll().stream().collect(Collectors.toList());
    }
}
