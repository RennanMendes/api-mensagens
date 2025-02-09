package br.com.magalu.api_mensagens.api.converter;

public interface DtoConverter<A, B> {

    A dtoToEntity(B dto);

    B entityToDto(A entity);
}
