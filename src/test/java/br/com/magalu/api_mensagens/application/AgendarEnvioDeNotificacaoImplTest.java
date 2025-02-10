package br.com.magalu.api_mensagens.application;

import br.com.magalu.api_mensagens.adapter.NotificacaoRepositoryService;
import br.com.magalu.api_mensagens.api.dto.DestinatarioDto;
import br.com.magalu.api_mensagens.core.entity.Canal;
import br.com.magalu.api_mensagens.core.entity.Destinatario;
import br.com.magalu.api_mensagens.core.entity.Notificacao;
import br.com.magalu.api_mensagens.core.entity.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AgendarEnvioDeNotificacaoImplTest {

    @InjectMocks
    private AgendarEnvioDeNotificacaoImpl agendarEnvioDeNotificacao;

    @Mock
    private NotificacaoRepositoryService repository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarNovaNotificacao() {
        LocalDateTime data = LocalDateTime.now();
        Notificacao request = novaNotificacao(null, data, null);
        Notificacao respostaEsperada = novaNotificacao(UUID.randomUUID(),data,Status.PENDENTE);

        when(repository.salvar(request.setStatus(Status.PENDENTE))).thenReturn(respostaEsperada);

        Notificacao response = agendarEnvioDeNotificacao.agendar(request);

        verify(repository).salvar(request.setStatus(Status.PENDENTE));
        verifyNoMoreInteractions(repository);

        assertEquals(respostaEsperada, response);
    }


    private Notificacao novaNotificacao(UUID id, LocalDateTime dataHora, Status status) {
        return new Notificacao(id, dataHora,
                new Destinatario("João Silva", "joao@email.com", "(11) 12345-6789"),
                "Mensagem de teste", Canal.EMAIL, status);
    }
}