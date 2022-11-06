package br.com.sgp.adapters.outbound.repository;

import br.com.sgp.adapters.inbound.entity.PedidoEntity;
import br.com.sgp.adapters.inbound.entity.TemporadaEntity;
import br.com.sgp.application.core.domain.StatusPagamento;
import br.com.sgp.application.core.domain.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

    List<PedidoEntity> findBySituacao(StatusPedido situacao);

    List<PedidoEntity> findByStatusPagamento(StatusPagamento statusPagamento);

    List<PedidoEntity> findByTemporadaAndSituacao(TemporadaEntity temporada, StatusPedido situacao);

    List<PedidoEntity> findByAlunoNome(String nomeAluno);

    List<PedidoEntity> findByDataBetween(OffsetDateTime startDay, OffsetDateTime endDay);
    
}
