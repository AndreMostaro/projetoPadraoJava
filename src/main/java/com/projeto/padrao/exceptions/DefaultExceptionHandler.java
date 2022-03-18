package com.projeto.padrao.exceptions;

import lombok.Getter;

public class DefaultExceptionHandler extends Exception {

    @Getter
    private ErrorResponse errorResponse = new ErrorResponse();

    public DefaultExceptionHandler(Integer httpCode) {
        this.errorResponse.setStatusCode(httpCode);
    }

    public DefaultExceptionHandler(Integer httpCode, String userMessage) {
        super(userMessage);
        this.errorResponse.setStatusCode(httpCode);
        this.errorResponse.setUserMessage(userMessage);
    }

    public DefaultExceptionHandler(Integer httpCode, String userMessage, Throwable cause) {
        super(userMessage, cause);
        this.errorResponse.setStatusCode(httpCode);
        this.errorResponse.setUserMessage(userMessage);
    }

    public DefaultExceptionHandler(Integer httpCode, String userMessage, String developerMessage, Throwable cause) {
        super(userMessage, cause);
        this.errorResponse.setStatusCode(httpCode);
        this.errorResponse.setUserMessage(userMessage);
        this.errorResponse.setDeveloperMessage(developerMessage);
    }

//    public DefaultExceptionHandler(Throwable cause) {
//        super(cause);
//        this.errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        this.errorResponse.setUserMessage("Operação inválida! Ocorreu um erro ao executar a operação.");
//        this.errorResponse.setDeveloperMessage(ExceptionUtils.getStackTrace(cause));
//    }
}
