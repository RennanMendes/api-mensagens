package br.com.magalu.api_mensagens.api.converter;

import br.com.magalu.api_mensagens.api.dto.NotificacaoDto;
import br.com.magalu.api_mensagens.core.entity.Notificacao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NotificacaoConverter implements DtoConverter<Notificacao, NotificacaoDto> {

    private final DestinatarioConverter converter;

    @Override
    public Notificacao dtoToEntity(NotificacaoDto dto) {
        return new Notificacao(dto.id(), dto.dataHora(),
                converter.dtoToEntity(dto.destinatario()), dto.mensagem(), dto.canal(), dto.status());
    }

    @Override
    public NotificacaoDto entityToDto(Notificacao entity) {
        return new NotificacaoDto(
                entity.getId(),
                entity.getDataHora(),
                converter.entityToDto(entity.getDestinatario()),
                entity.getMensagem(),
                entity.getCanal(),
                entity.getStatus());
    }
}