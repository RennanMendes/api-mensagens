package br.com.magalu.api_mensagens.application;

import br.com.magalu.api_mensagens.adapter.NotificacaoRepositoryService;
import br.com.magalu.api_mensagens.application.exception.NotificacaoNaoEncontradaException;
import br.com.magalu.api_mensagens.core.cases.ConsultarEnvioDeNotificacao;
import br.com.magalu.api_mensagens.core.entity.Notificacao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ConsultarEnvioDeNotificacaoImpl implements ConsultarEnvioDeNotificacao {

    private final NotificacaoRepositoryService repository;

    @Override
    public Notificacao consultar(UUID id) {
        return repository.buscarPorId(id).orElseThrow(NotificacaoNaoEncontradaException::new);
    }

}