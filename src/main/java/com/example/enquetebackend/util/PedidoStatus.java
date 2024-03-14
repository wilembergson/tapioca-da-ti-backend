package com.example.enquetebackend.util;

import com.example.enquetebackend.exceptions.ErroPadrao;
import org.springframework.http.HttpStatus;

public enum PedidoStatus {
    CRIANDO(1, "CRIANDO"),
    FEITO(2, "FEITO"),
    EM_TRANSPORTE(3, "À CAMINHO"),
    ENTREGUE(4, "ENTREGUE"),
    FINALIZADO(5, "FINALIZADO");

    private Integer id;
    private String descricao;

    private PedidoStatus(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static PedidoStatus getById(int id) {
        for (PedidoStatus resposta : values())
            if (resposta.id == id)
                return resposta;
        throw new ErroPadrao("Resposta Inválida: "+id, HttpStatus.BAD_REQUEST);
    }

    public Integer getID() {
        return id;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    @Override
    public String toString() {
        return getDescricao();
    }

}

