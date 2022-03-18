package com.projeto.padrao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelDTO {

    private Integer id;
    private String nome;
    private String cpf;
    private String email;
    private String ativoInativo;

}
