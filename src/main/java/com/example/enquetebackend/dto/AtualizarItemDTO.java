package com.example.enquetebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtualizarItemDTO {

    private Integer id;

    private Integer quantidade;

    private Integer sabor_id;
}
