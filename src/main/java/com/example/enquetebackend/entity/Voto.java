package com.example.enquetebackend.entity;

import com.example.enquetebackend.util.RespostasEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="AGO_VOTO")
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Voto {
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "RESPOSTA")
    private String resposta;

    @Column(name = "CRM")
    private String crm;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "DT_VOTO")
    private LocalDateTime data_hora;

    @ManyToOne
    @JoinColumn(name="ID_ENQUETE")
    @JsonBackReference
    private Enquete enquete;

    public void setResposta(Integer idResposta){
        this.resposta = RespostasEnum.getById(idResposta).getDescricao();
    }
}
