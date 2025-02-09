package br.com.magalu.api_mensagens.api.handler;

import org.springframework.validation.FieldError;

public record DataErrorDto(String field, String message) {
    public DataErrorDto(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}