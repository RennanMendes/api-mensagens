package br.com.magalu.api_mensagens.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DestinatarioDto(
        @NotBlank String nome,
        @NotNull @Email String email,
        @NotBlank String telefone
) {
}