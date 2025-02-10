package br.com.magalu.api_mensagens.application;

import br.com.magalu.api_mensagens.adapter.NotificacaoRepositoryService;
import br.com.magalu.api_mensagens.application.exception.NotificacaoNaoEncontradaException;
import br.com.magalu.api_mensagens.core.entity.Canal;
import br.com.magalu.api_mensagens.core.entity.Destinatario;
import br.com.magalu.api_mensagens.core.entity.Notificacao;
import br.com.magalu.api_mensagens.core.entity.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsultarEnvioDeNotificacaoImplTest {

    @InjectMocks
    private ConsultarEnvioDeNotificacaoImpl consultarEnvioDeNotificacao;

    @Mock
    private NotificacaoRepositoryService repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarAgendamento_quandoIdValido() {
        UUID id = UUID.randomUUID();
        Notificacao respostaEsperada = novaNotificacao(id);

        when(repository.buscarPorId(id)).thenReturn(Optional.of(respostaEsperada));

        Notificacao resposta = consultarEnvioDeNotificacao.consultar(id);

        verify(repository).buscarPorId(id);
        verifyNoMoreInteractions(repository);

        assertEquals(respostaEsperada, resposta);
    }

    @Test
    void deveRetornarNotificacaoNaoEncontradaException_quandoIdInvalido() {
        UUID id = UUID.randomUUID();

        when(repository.buscarPorId(id)).thenThrow(new NotificacaoNaoEncontradaException());

        assertThrows(NotificacaoNaoEncontradaException.class, () -> consultarEnvioDeNotificacao.consultar(id));

        verify(repository).buscarPorId(id);
        verifyNoMoreInteractions(repository);
    }


    private Notificacao novaNotificacao(UUID id) {
        return new Notificacao(id, LocalDateTime.now(),
                new Destinatario("João Silva", "joao@email.com", "(11) 12345-6789"),
                "Mensagem de teste", Canal.EMAIL, Status.PENDENTE);
    }
}