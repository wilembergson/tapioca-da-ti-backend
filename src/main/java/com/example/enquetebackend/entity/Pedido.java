package com.example.enquetebackend.entity;

import com.example.enquetebackend.util.PedidoStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="PEDIDO")
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "PIX")
    private String pix;

    @Column(name = "STATUS")
    private String status;

    @Column(name="DATA")
    private LocalDateTime data;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Item> itens;

    public Pedido(String pix, LocalDateTime data){
        this.pix = pix;
        this.status = PedidoStatus.getById(1).getDescricao();
        this.data = data;
    }

    public Double getTotal(){
        return this.itens.stream()
                .mapToDouble(Item::getTotal)
                .sum();
    }

    public Double getTotalPago(){
        return this.itens.stream()
                .filter(item -> item.getPago().equals(true))
                .mapToDouble(Item::getTotal)
                .sum();
    }

    public Double getTotalAPagar(){
        return this.itens.stream()
                .filter(item -> item.getPago().equals(false))
                .mapToDouble(Item::getTotal)
                .sum();
    }
}
