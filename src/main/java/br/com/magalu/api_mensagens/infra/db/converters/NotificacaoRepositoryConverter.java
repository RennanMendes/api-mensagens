package br.com.magalu.api_mensagens.infra.db.converters;

import br.com.magalu.api_mensagens.core.entity.Notificacao;
import br.com.magalu.api_mensagens.infra.db.entity.NotificacaoEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NotificacaoRepositoryConverter implements RepositoryConverter<NotificacaoEntity, Notificacao> {

    private final DestinatarioRepositoryConverter converter;

    @Override
    public NotificacaoEntity mapToTable(Notificacao notificacao) {
        return new NotificacaoEntity(
                notificacao.getId(),
                notificacao.getDataHora(),
                converter.mapToTable(notificacao.getDestinatario()),
                notificacao.getMensagem(),
                notificacao.getCanal(),
                notificacao.getStatus());
    }

    @Override
    public Notificacao mapToEntity(NotificacaoEntity entity) {
        return new Notificacao(
                entity.getId(),
                entity.getDataHora(),
                converter.mapToEntity(entity.getDestinatario()),
                entity.getMensagem(),
                entity.getCanal(),
                entity.getStatus());
    }
}