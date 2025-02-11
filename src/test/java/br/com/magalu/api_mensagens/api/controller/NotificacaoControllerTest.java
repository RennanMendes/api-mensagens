package br.com.magalu.api_mensagens.api.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.magalu.api_mensagens.api.converter.NotificacaoConverter;
import br.com.magalu.api_mensagens.api.dto.DestinatarioDto;
import br.com.magalu.api_mensagens.api.dto.NotificacaoDto;
import br.com.magalu.api_mensagens.core.cases.AgendarEnvioDeNotificacao;
import br.com.magalu.api_mensagens.core.cases.CancelarEnvioDeNotificacao;
import br.com.magalu.api_mensagens.core.cases.ConsultarEnvioDeNotificacao;
import br.com.magalu.api_mensagens.core.entity.Canal;
import br.com.magalu.api_mensagens.core.entity.Destinatario;
import br.com.magalu.api_mensagens.core.entity.Notificacao;
import br.com.magalu.api_mensagens.core.entity.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.UUID;

@WebMvcTest(NotificacaoController.class)
@ExtendWith(MockitoExtension.class)
class NotificacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AgendarEnvioDeNotificacao agendarEnvioDeNotificacao;

    @MockitoBean
    private ConsultarEnvioDeNotificacao consultarEnvioDeNotificacao;

    @MockitoBean
    private CancelarEnvioDeNotificacao cancelarEnvioDeNotificacao;

    @MockitoBean
    private NotificacaoConverter converter;

    private UUID id;

    private final String BASE_URL = "/notificacao";

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
    }

    @Test
    @SneakyThrows
    void deveRetornar200_quandoBuscarAgendamentoPorId() {
        NotificacaoDto dto = new NotificacaoDto(
                id,
                LocalDateTime.now().plusDays(1),
                new DestinatarioDto("João Silva", "joao@email.com", "(11) 12345-6789"),
                "Mensagem de teste",
                Canal.EMAIL,
                null
        );

        Notificacao notificacao = novaNotificacao(id);

        when(consultarEnvioDeNotificacao.consultar(id)).thenReturn(notificacao);
        when(converter.entityToDto(notificacao)).thenReturn(dto);

        mockMvc.perform(get(BASE_URL + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.destinatario.nome").value(dto.destinatario().nome()))
                .andExpect(jsonPath("$.destinatario.email").value(dto.destinatario().email()))
                .andExpect(jsonPath("$.destinatario.telefone").value(dto.destinatario().telefone()))
                .andExpect(jsonPath("$.mensagem").value(dto.mensagem()))
                .andExpect(jsonPath("$.canal").value(dto.canal().toString()));

        verify(consultarEnvioDeNotificacao).consultar(id);
        verify(converter).entityToDto(notificacao);

        verifyNoMoreInteractions(consultarEnvioDeNotificacao, converter);
        verifyNoInteractions(agendarEnvioDeNotificacao, cancelarEnvioDeNotificacao);
    }

    @Test
    @SneakyThrows
    void deveRetornar400_quandoBuscarAgendamentoPorIdInvalido() {
        NotificacaoDto dto = new NotificacaoDto(
                id,
                LocalDateTime.now().plusDays(1),
                new DestinatarioDto("João Silva", "joao@email.com", "(11) 12345-6789"),
                "Mensagem de teste",
                Canal.EMAIL,
                null
        );

        when(consultarEnvioDeNotificacao.consultar(id)).thenThrow(MethodArgumentTypeMismatchException.class);

        mockMvc.perform(get(BASE_URL + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        verify(consultarEnvioDeNotificacao).consultar(id);
        verifyNoMoreInteractions(consultarEnvioDeNotificacao);
        verifyNoInteractions(converter, agendarEnvioDeNotificacao, cancelarEnvioDeNotificacao);
    }

    @Test
    @SneakyThrows
    void deveRetornar200_quandoAgendarEmailComDadosValidos() {
        NotificacaoDto dto = new NotificacaoDto(
                null,
                LocalDateTime.now().plusDays(1),
                new DestinatarioDto("João Silva", "joao@email.com", "(11) 12345-6789"),
                "Mensagem de teste",
                Canal.EMAIL,
                null
        );

        Notificacao notificacao = novaNotificacao(id);

        when(converter.dtoToEntity(dto)).thenReturn(notificacao);
        when(agendarEnvioDeNotificacao.agendar(notificacao)).thenReturn(notificacao);
        when(converter.entityToDto(notificacao)).thenReturn(dto);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.destinatario.nome").value(dto.destinatario().nome()))
                .andExpect(jsonPath("$.destinatario.email").value(dto.destinatario().email()))
                .andExpect(jsonPath("$.destinatario.telefone").value(dto.destinatario().telefone()))
                .andExpect(jsonPath("$.mensagem").value(dto.mensagem()))
                .andExpect(jsonPath("$.canal").value(dto.canal().toString()));

        verify(converter).dtoToEntity(dto);
        verify(agendarEnvioDeNotificacao).agendar(notificacao);
        verify(converter).entityToDto(notificacao);

        verifyNoMoreInteractions(agendarEnvioDeNotificacao, converter);
        verifyNoInteractions(consultarEnvioDeNotificacao, cancelarEnvioDeNotificacao);
    }

    @Test
    @SneakyThrows
    void deveRetornar400_quandoAgendarEmailComNomeDestinatarioInvalido() {
        NotificacaoDto dto = new NotificacaoDto(
                null,
                LocalDateTime.now().plusDays(1),
                new DestinatarioDto("", "email_invalido", "(11) 12345-6789"),
                "Mensagem de teste",
                Canal.EMAIL,
                null
        );

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(converter, agendarEnvioDeNotificacao, cancelarEnvioDeNotificacao, consultarEnvioDeNotificacao);
    }

    @Test
    @SneakyThrows
    void deveRetornar400_quandoAgendarEmailComEmailDestinatarioInvalido() {
        NotificacaoDto dto = new NotificacaoDto(
                null,
                LocalDateTime.now().plusDays(1),
                new DestinatarioDto("João Silva", "joao.silva", "(11) 12345-6789"),
                "Mensagem de teste",
                Canal.EMAIL,
                null
        );

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(converter, agendarEnvioDeNotificacao, cancelarEnvioDeNotificacao, consultarEnvioDeNotificacao);
    }

    @Test
    @SneakyThrows
    void deveRetornar400_quandoAgendarEmailComTelefoneDestinatarioInvalido() {
        NotificacaoDto dto = new NotificacaoDto(
                null,
                LocalDateTime.now().plusDays(1),
                new DestinatarioDto("João Silva", "email_invalido", ""),
                "Mensagem de teste",
                Canal.EMAIL,
                null
        );

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(converter, agendarEnvioDeNotificacao, cancelarEnvioDeNotificacao, consultarEnvioDeNotificacao);
    }

    @Test
    @SneakyThrows
    void deveRetornar400_quandoAgendarEmailComDataHoraNoPassado() {
        NotificacaoDto dto = new NotificacaoDto(
                null,
                LocalDateTime.now().minusDays(1),
                new DestinatarioDto("João Silva", "joao@email.com", "(11) 12345-6789"),
                "Mensagem de teste",
                Canal.EMAIL,
                null
        );

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(converter, agendarEnvioDeNotificacao, cancelarEnvioDeNotificacao, consultarEnvioDeNotificacao);
    }

    @Test
    @SneakyThrows
    void deveRetornar400_quandoAgendarEmailComCanalInvalido() {
        NotificacaoDto dto = new NotificacaoDto(
                null,
                LocalDateTime.now().minusDays(1),
                new DestinatarioDto("João Silva", "joao@email.com", "(11) 12345-6789"),
                "Mensagem de teste",
                null,
                null
        );

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(converter, agendarEnvioDeNotificacao, cancelarEnvioDeNotificacao, consultarEnvioDeNotificacao);
    }

    @Test
    @SneakyThrows
    void deveRetornar202_quandoCancelarEnvio() {
        NotificacaoDto dto = new NotificacaoDto(
                id,
                LocalDateTime.now().plusDays(1),
                new DestinatarioDto("João Silva", "joao@email.com", "(11) 12345-6789"),
                "Mensagem de teste",
                Canal.EMAIL,
                null
        );

        Notificacao notificacao = novaNotificacao(id);

        when(cancelarEnvioDeNotificacao.cancelar(id)).thenReturn(notificacao);
        when(converter.entityToDto(notificacao)).thenReturn(dto);

        mockMvc.perform(delete(BASE_URL + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        verify(cancelarEnvioDeNotificacao).cancelar(id);
        verify(converter).entityToDto(notificacao);

        verifyNoMoreInteractions(cancelarEnvioDeNotificacao, converter);
        verifyNoInteractions(agendarEnvioDeNotificacao, consultarEnvioDeNotificacao);
    }

    private Notificacao novaNotificacao(UUID id) {
        return new Notificacao(id, LocalDateTime.now(),
                new Destinatario("João Silva", "joao@email.com", "(11) 12345-6789"),
                "Mensagem de teste", Canal.EMAIL, Status.CANCELADO);
    }

}