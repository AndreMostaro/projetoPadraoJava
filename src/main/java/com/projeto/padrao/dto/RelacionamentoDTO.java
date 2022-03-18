package com.projeto.padrao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelacionamentoDTO {

    private Integer id;

    private ModelDTO modelDTO;
}
