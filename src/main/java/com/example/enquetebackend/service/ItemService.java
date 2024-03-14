package com.example.enquetebackend.service;

import com.example.enquetebackend.dto.AtualizarItemDTO;
import com.example.enquetebackend.dto.NovoItemDTO;
import com.example.enquetebackend.dto.VotoDTO;
import com.example.enquetebackend.entity.*;
import com.example.enquetebackend.exceptions.ErroPadrao;
import com.example.enquetebackend.repository.*;
import com.example.enquetebackend.util.PedidoStatus;
import com.example.enquetebackend.util.RespostasEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final SaborRepository saborRepository;

    private final PedidoRepository pedidoRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, SaborRepository saborRepository, PedidoRepository pedidoRepository){
        this.itemRepository = itemRepository;
        this.saborRepository = saborRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public void novoItem(NovoItemDTO dto){
        Optional<Pedido> pedidoOp = pedidoRepository.findByStatus(PedidoStatus.CRIANDO.getDescricao());
        if(pedidoOp.isEmpty()) throw new ErroPadrao("Pedido não encontrado.", HttpStatus.NOT_FOUND);
        Optional<Sabor> saborOp = saborRepository.findById(dto.getSabor_id());
        if(saborOp.isEmpty()) throw new ErroPadrao("Sabor não encontrado.", HttpStatus.NOT_FOUND);
        Item item = new Item(
                dto.getNomeCliente(),
                dto.getQuantidade(),
                saborOp.get(),
                pedidoOp.get()
        );
        itemRepository.save(item);
    }

    public void atualizarItem(AtualizarItemDTO dto){
        Optional<Item> itemOp = itemRepository.findById(dto.getId());
        if(itemOp.isEmpty()) throw new ErroPadrao("Item não encontrado.", HttpStatus.NOT_FOUND);
        Optional<Sabor> saborOp = saborRepository.findById(dto.getSabor_id());
        if(saborOp.isEmpty()) throw new ErroPadrao("Sabor não encontrado.", HttpStatus.NOT_FOUND);
        Item item = itemOp.get();
        item.setSabor(saborOp.get());
        item.setQuantidade(dto.getQuantidade());
        itemRepository.save(item);
    }

    public void mudarStatusPagamento(Integer id){
        Optional<Item> itemOp = itemRepository.findById(id);
        if(itemOp.isEmpty()) throw new ErroPadrao("Item não encontrado.", HttpStatus.NOT_FOUND);
        Item item = itemOp.get();
        if(item.getPago().equals(false)){
            item.setPago(true);
        }else{
            item.setPago(false);
        }
        itemRepository.save(item);
    }

    public List<Item> listarItens(){
        return itemRepository.findAll().stream().collect(Collectors.toList());
    }

    public Item obterPorId(Integer id){
        return itemRepository.findById(id)
                .orElseThrow(() -> new ErroPadrao("Item não encontrado.", HttpStatus.CONFLICT));
    }
    public void deletarPorId(Integer id){
        obterPorId(id);
        itemRepository.deleteById(id);
    }
}
