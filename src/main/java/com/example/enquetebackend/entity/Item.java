package com.example.enquetebackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ITEM")
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NOME_CLIENTE")
    private String nomeCliente;

    @Column(name = "QUANTIDADE")
    private Integer quantidade;

    @Column(name = "PAGO")
    private Boolean pago;

    @ManyToOne
    @JoinColumn(name = "ID_SABOR")
    @JsonManagedReference
    private Sabor sabor;

    @ManyToOne
    @JoinColumn(name="ID_PEDIDO")
    @JsonBackReference
    private Pedido pedido;

    public Item(String nomeCliente, Integer quantidade, Sabor sabor, Pedido pedido) {
        this.nomeCliente = nomeCliente;
        this.quantidade = quantidade;
        this.pago = false;
        this.sabor = sabor;
        this.pedido = pedido;
    }

    public Double getTotal(){
        return quantidade * sabor.getPreco();
    }
}
