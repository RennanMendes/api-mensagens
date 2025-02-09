package br.com.magalu.api_mensagens.core.cases;

import br.com.magalu.api_mensagens.core.entity.Notificacao;

public interface AgendarEnvioDeNotificacao {
    Notificacao agendar(Notificacao notificacao);
}
