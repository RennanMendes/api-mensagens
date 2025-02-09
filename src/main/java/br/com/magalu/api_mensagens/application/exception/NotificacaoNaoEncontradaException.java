package br.com.magalu.api_mensagens.application.exception;

public class NotificacaoNaoEncontradaException extends RuntimeException {
    public NotificacaoNaoEncontradaException() {
        super("Agendamento não encontrado!");
    }
}
