package com.example.enquetebackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="SABOR")
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Sabor {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "PRECO")
    private Double preco;

    @OneToMany(mappedBy = "sabor")
    @JsonBackReference
    private List<Item> items = new ArrayList<>();
}
