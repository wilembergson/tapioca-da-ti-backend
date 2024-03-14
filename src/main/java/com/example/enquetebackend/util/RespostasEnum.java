package com.example.enquetebackend.util;

import com.example.enquetebackend.exceptions.ErroPadrao;
import org.springframework.http.HttpStatus;

public enum RespostasEnum {
    APROVAR(1, "APROVAR"),
    REPROVAR(2, "REPROVAR"),
    ABSTER(3, "ABSTER");

    private Integer id;
    private String descricao;

    private RespostasEnum(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static RespostasEnum getById(int id) {
        for (RespostasEnum resposta : values())
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

