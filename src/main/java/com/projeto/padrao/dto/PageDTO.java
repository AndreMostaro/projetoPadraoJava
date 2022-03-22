package com.projeto.padrao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageDTO<T> {

    private BigDecimal totalElements;
    private BigDecimal totalPages;
    private boolean isFirst;
    private boolean isLast;
    private List<T> content;
}
