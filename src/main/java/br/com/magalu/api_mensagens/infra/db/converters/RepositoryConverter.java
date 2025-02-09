package br.com.magalu.api_mensagens.infra.db.converters;

import br.com.magalu.api_mensagens.infra.db.entity.NotificacaoEntity;

import java.util.Optional;

public interface RepositoryConverter<T, E> {

    T mapToTable(final E persistenceObject);

    E mapToEntity(final T entity);

}