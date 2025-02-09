package br.com.magalu.api_mensagens.infra.db.implementation;

import br.com.magalu.api_mensagens.adapter.NotificacaoRepositoryService;
import br.com.magalu.api_mensagens.core.entity.Notificacao;
import br.com.magalu.api_mensagens.infra.db.converters.NotificacaoRepositoryConverter;
import br.com.magalu.api_mensagens.infra.db.entity.NotificacaoEntity;
import br.com.magalu.api_mensagens.infra.db.repository.NotificacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NotificacaoRepositoryServiceImpl implements NotificacaoRepositoryService {

    private final NotificacaoRepository repository;
    private final NotificacaoRepositoryConverter converter;

    @Override
    public Notificacao salvar(Notificacao notificacao) {
        NotificacaoEntity entity = converter.mapToTable(notificacao);
        return converter.mapToEntity(repository.save(entity));
    }

    @Override
    public Optional<Notificacao> buscarPorId(UUID id) {
        Optional<NotificacaoEntity> entity = repository.findById(id);
        return entity.map(converter::mapToEntity);
    }

    @Override
    public void cancelar(Notificacao notificacao) {

    }
}
