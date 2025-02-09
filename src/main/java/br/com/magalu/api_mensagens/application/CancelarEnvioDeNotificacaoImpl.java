package br.com.magalu.api_mensagens.application;

import br.com.magalu.api_mensagens.adapter.NotificacaoRepositoryService;
import br.com.magalu.api_mensagens.application.exception.NotificacaoNaoEncontradaException;
import br.com.magalu.api_mensagens.core.cases.CancelarEnvioDeNotificacao;
import br.com.magalu.api_mensagens.core.entity.Notificacao;
import br.com.magalu.api_mensagens.core.entity.Status;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CancelarEnvioDeNotificacaoImpl implements CancelarEnvioDeNotificacao {

    private final NotificacaoRepositoryService repository;

    @Override
    public Notificacao cancelar(UUID id) {
        Notificacao notificacao = repository.buscarPorId(id).orElseThrow(NotificacaoNaoEncontradaException::new);
        return repository.salvar(notificacao.setStatus(Status.CANCELADO));
    }
}
