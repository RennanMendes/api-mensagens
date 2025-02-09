package br.com.magalu.api_mensagens.api.controller;

import br.com.magalu.api_mensagens.api.converter.NotificacaoConverter;
import br.com.magalu.api_mensagens.api.dto.NotificacaoDto;
import br.com.magalu.api_mensagens.core.cases.AgendarEnvioDeNotificacao;
import br.com.magalu.api_mensagens.core.cases.CancelarEnvioDeNotificacao;
import br.com.magalu.api_mensagens.core.cases.ConsultarEnvioDeNotificacao;
import br.com.magalu.api_mensagens.core.entity.Notificacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/notificacao")
public class NotificacaoController {

    private final AgendarEnvioDeNotificacao agendarEnvioDeNotificacao;
    private final ConsultarEnvioDeNotificacao consultarEnvioDeNotificacao;
    private final CancelarEnvioDeNotificacao cancelarEnvioDeNotificacao;
    private final NotificacaoConverter converter;

    @Autowired
    public NotificacaoController(AgendarEnvioDeNotificacao agendarEnvioDeNotificacao, ConsultarEnvioDeNotificacao consultarEnvioDeNotificacao, CancelarEnvioDeNotificacao cancelarEnvioDeNotificacao, NotificacaoConverter converter) {
        this.agendarEnvioDeNotificacao = agendarEnvioDeNotificacao;
        this.consultarEnvioDeNotificacao = consultarEnvioDeNotificacao;
        this.cancelarEnvioDeNotificacao = cancelarEnvioDeNotificacao;
        this.converter = converter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacaoDto> consultarEnvio(@PathVariable UUID id) {
        NotificacaoDto dto = converter.entityToDto(consultarEnvioDeNotificacao.consultar(id));
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<NotificacaoDto> agendarEnvio(@RequestBody @Validated NotificacaoDto dto, UriComponentsBuilder uriBuilder) {
        Notificacao response = agendarEnvioDeNotificacao.agendar(converter.dtoToEntity(dto));
        URI uri = uriBuilder.path("/notificacao/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(converter.entityToDto(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NotificacaoDto> cancelarEnvio(@PathVariable UUID id){
        NotificacaoDto dto = converter.entityToDto(cancelarEnvioDeNotificacao.cancelar(id));
        return ResponseEntity.accepted().body(dto);
    }

}