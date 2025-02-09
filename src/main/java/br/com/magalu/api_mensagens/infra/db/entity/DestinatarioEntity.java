package br.com.magalu.api_mensagens.infra.db.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DestinatarioEntity {
    private String nome;
    private String email;
    private String telefone;
}