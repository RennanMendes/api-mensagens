package br.com.magalu.api_mensagens.infra.db.repository;

import br.com.magalu.api_mensagens.infra.db.entity.NotificacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificacaoRepository extends JpaRepository<NotificacaoEntity, UUID> {
}
