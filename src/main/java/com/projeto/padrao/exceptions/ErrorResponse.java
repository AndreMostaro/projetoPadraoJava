package com.projeto.padrao.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private Integer errorCode;
    private Integer statusCode;
    private String userMessage;
    private String developerMessage;
    private List<String> details;
}
