package br.com.magalu.api_mensagens.application;

import br.com.magalu.api_mensagens.adapter.NotificacaoRepositoryService;
import br.com.magalu.api_mensagens.core.cases.AgendarEnvioDeNotificacao;
import br.com.magalu.api_mensagens.core.entity.Notificacao;
import br.com.magalu.api_mensagens.core.entity.Status;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AgendarEnvioDeNotificacaoImpl implements AgendarEnvioDeNotificacao {

    private final NotificacaoRepositoryService repository;

    @Override
    public Notificacao agendar(Notificacao notificacao) {
        return repository.salvar(notificacao.setStatus(Status.PENDENTE));
    }
}