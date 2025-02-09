package br.com.magalu.api_mensagens.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Notificacao {
    private UUID id;
    private LocalDateTime dataHora;
    private Destinatario destinatario;
    private String mensagem;
    private Canal canal;
    private Status status;

    public Notificacao setStatus(Status status) {
        this.status = status;
        return this;
    }
}
