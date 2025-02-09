package br.com.magalu.api_mensagens.adapter;

import br.com.magalu.api_mensagens.core.entity.Notificacao;

import java.util.Optional;
import java.util.UUID;

public interface NotificacaoRepositoryService {
    Notificacao salvar(Notificacao notificacao);
    Optional<Notificacao> buscarPorId(UUID id);
    void cancelar(Notificacao notificacao);
}
