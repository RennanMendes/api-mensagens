package br.com.magalu.api_mensagens.application;

import br.com.magalu.api_mensagens.adapter.NotificacaoRepositoryService;
import br.com.magalu.api_mensagens.core.cases.CancelarEnvioDeNotificacao;
import br.com.magalu.api_mensagens.core.cases.ConsultarEnvioDeNotificacao;
import br.com.magalu.api_mensagens.core.entity.Notificacao;
import br.com.magalu.api_mensagens.core.entity.Status;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CancelarEnvioDeNotificacaoImpl implements CancelarEnvioDeNotificacao {

    private final NotificacaoRepositoryService repository;
    private final ConsultarEnvioDeNotificacao consultarEnvioDeNotificacao;

    @Override
    public Notificacao cancelar(UUID id) {
        Notificacao notificacao = consultarEnvioDeNotificacao.consultar(id);
        return repository.salvar(notificacao.setStatus(Status.CANCELADO));
    }
}
