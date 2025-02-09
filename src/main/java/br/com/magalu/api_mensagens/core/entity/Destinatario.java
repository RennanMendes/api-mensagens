package br.com.magalu.api_mensagens.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Destinatario {
    private String nome;
    private String email;
    private String telefone;
}