package br.com.magalu.api_mensagens.api.dto;

import br.com.magalu.api_mensagens.core.entity.Canal;
import br.com.magalu.api_mensagens.core.entity.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificacaoDto(
        UUID id,
        @Future @NotNull LocalDateTime dataHora,
        @Valid @NotNull DestinatarioDto destinatario,
        @NotNull String mensagem,
        @NotNull Canal canal,
        Status status
) {
}