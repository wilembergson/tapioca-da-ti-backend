package com.example.enquetebackend.service;

import com.example.enquetebackend.dto.PedidoDTO;
import com.example.enquetebackend.entity.Pedido;
import com.example.enquetebackend.exceptions.ErroPadrao;
import com.example.enquetebackend.repository.PedidoRepository;
import com.example.enquetebackend.util.PedidoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public void novoPedido(PedidoDTO dto){
        if(obterPedidoEmCriacao() != null)
            throw new ErroPadrao("Existe um pedido sem ser finalizado.", HttpStatus.CONFLICT);
        Pedido pedido = new Pedido(
                dto.getPix(),
                LocalDateTime.now()
        );
        pedidoRepository.save(pedido);
    }

    public void atualizarPix(PedidoDTO dto){
        Pedido pedido = obterPedidoEmCriacao();
        if(pedido == null)
            throw new ErroPadrao("Nenhum pedido sendo montado no momento.", HttpStatus.NOT_FOUND);
        pedido.setPix(dto.getPix());
        pedidoRepository.save(pedido);
    }

    public Pedido obterPedidoEmCriacao(){
        Optional<Pedido> pedido = pedidoRepository.findAll().stream().findFirst();
        return pedido.orElse(null);
    }

    public void atualizarStatus(Integer statusId){
        Pedido pedido = obterPedidoEmCriacao();
        if(pedido == null) throw new ErroPadrao("Pedido não encontrado.", HttpStatus.NOT_FOUND);
        pedido.setStatus(PedidoStatus.getById(statusId).getDescricao());
        pedidoRepository.save(pedido);
    }

    public void deletar(){
        Pedido pedido = obterPedidoEmCriacao();
        if(pedido == null) throw new ErroPadrao("Pedido não encontrado.", HttpStatus.NOT_FOUND);
        pedidoRepository.delete(pedido);
    }
}
