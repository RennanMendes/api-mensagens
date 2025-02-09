package br.com.magalu.api_mensagens.core.cases;

import br.com.magalu.api_mensagens.core.entity.Notificacao;

import java.util.UUID;

public interface CancelarEnvioDeNotificacao {
    Notificacao cancelar(UUID id);
}