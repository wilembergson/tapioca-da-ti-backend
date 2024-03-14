package com.example.enquetebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoDTO {
    private String pergunta;

    private Integer aprovar;

    private Integer reprovar;

    private Integer abster;

    private Integer total;

    private String resultado;
}
