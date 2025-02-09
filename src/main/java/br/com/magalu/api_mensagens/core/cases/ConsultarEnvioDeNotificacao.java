package br.com.magalu.api_mensagens.core.cases;

import br.com.magalu.api_mensagens.core.entity.Notificacao;

import java.util.UUID;

public interface ConsultarEnvioDeNotificacao {
    Notificacao consultar (UUID id);
}