package br.com.magalu.api_mensagens.api.converter;

import br.com.magalu.api_mensagens.api.dto.DestinatarioDto;
import br.com.magalu.api_mensagens.core.entity.Destinatario;
import org.springframework.stereotype.Component;

@Component
public class DestinatarioConverter implements DtoConverter<Destinatario, DestinatarioDto> {
    @Override
    public Destinatario dtoToEntity(DestinatarioDto dto) {
        return new Destinatario(dto.nome(), dto.email(), dto.telefone());
    }

    @Override
    public DestinatarioDto entityToDto(Destinatario entity) {
        return new DestinatarioDto(entity.getNome(), entity.getEmail(), entity.getTelefone());
    }
}
