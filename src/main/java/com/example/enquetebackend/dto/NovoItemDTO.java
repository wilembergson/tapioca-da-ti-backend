package com.example.enquetebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NovoItemDTO {

    private String nomeCliente;

    private Integer quantidade;

    private Integer sabor_id;
}
