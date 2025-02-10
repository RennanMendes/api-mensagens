package br.com.magalu.api_mensagens.application;

import br.com.magalu.api_mensagens.adapter.NotificacaoRepositoryService;
import br.com.magalu.api_mensagens.application.exception.NotificacaoNaoEncontradaException;
import br.com.magalu.api_mensagens.core.cases.ConsultarEnvioDeNotificacao;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CancelarEnvioDeNotificacaoImplTest {

    @InjectMocks
    private CancelarEnvioDeNotificacaoImpl cancelarEnvioDeNotificacao;

    @Mock
    private NotificacaoRepositoryService repository;

    @Mock
    private ConsultarEnvioDeNotificacao consultarEnvioDeNotificacao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCancelarAgendamento_quandoIdValido() {
        UUID id = UUID.randomUUID();

        Notificacao respostaEsperada = novaNotificacao(id);

        when(consultarEnvioDeNotificacao.consultar(id)).thenReturn(respostaEsperada);
        when(repository.salvar(respostaEsperada)).thenReturn(respostaEsperada);

        Notificacao resposta = cancelarEnvioDeNotificacao.cancelar(id);

        verify(consultarEnvioDeNotificacao).consultar(id);
        verify(repository).salvar(respostaEsperada);

        verifyNoMoreInteractions(consultarEnvioDeNotificacao, repository);

        assertEquals(Status.CANCELADO, resposta.getStatus());
        assertEquals(respostaEsperada, resposta);
    }

    @Test
    void deveRetornarNotificacaoNaoEncontradaException_quandoIdInvalido() {
        UUID id = UUID.randomUUID();

        when(consultarEnvioDeNotificacao.consultar(id)).thenThrow(new NotificacaoNaoEncontradaException());

        assertThrows(NotificacaoNaoEncontradaException.class, () -> cancelarEnvioDeNotificacao.cancelar(id));

        verify(consultarEnvioDeNotificacao).consultar(id);
        verifyNoMoreInteractions(consultarEnvioDeNotificacao);
        verifyNoInteractions(repository);
    }


    private Notificacao novaNotificacao(UUID id) {
        return new Notificacao(id, LocalDateTime.now(),
                new Destinatario("João Silva", "joao@email.com", "(11) 12345-6789"),
                "Mensagem de teste", Canal.EMAIL, Status.CANCELADO);
    }

}