package br.com.magalu.api_mensagens.infra.db.converters;

import br.com.magalu.api_mensagens.core.entity.Destinatario;
import br.com.magalu.api_mensagens.infra.db.entity.DestinatarioEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DestinatarioRepositoryConverter implements RepositoryConverter<DestinatarioEntity, Destinatario> {

    @Override
    public DestinatarioEntity mapToTable(Destinatario destinatario) {
        return new DestinatarioEntity(destinatario.getNome(), destinatario.getEmail(), destinatario.getTelefone());
    }

    @Override
    public Destinatario mapToEntity(DestinatarioEntity destinatario) {
        return new Destinatario(destinatario.getNome(), destinatario.getEmail(), destinatario.getTelefone());
    }
}