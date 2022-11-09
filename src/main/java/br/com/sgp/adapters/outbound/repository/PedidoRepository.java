package br.com.sgp.adapters.outbound.repository;

import br.com.sgp.adapters.inbound.entity.PedidoEntity;
import br.com.sgp.adapters.inbound.entity.TemporadaEntity;
import br.com.sgp.application.core.domain.StatusPagamento;
import br.com.sgp.application.core.domain.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

    List<PedidoEntity> findAllByTemporadaId(Long temporadaId);
    List<PedidoEntity> findBySituacaoAndTemporadaId(StatusPedido situacao, Long temporadaId);

    List<PedidoEntity> findByStatusPagamentoAndTemporadaId(StatusPagamento statusPagamento, Long temporadaId);

    List<PedidoEntity> findByTemporadaAndSituacao(TemporadaEntity temporada, StatusPedido situacao);

    List<PedidoEntity> findByAlunoNomeAndTemporadaId(String nomeAluno, Long temporadaId);

    List<PedidoEntity> findByDataBetweenAndTemporadaId(OffsetDateTime startDay, OffsetDateTime endDay, Long temporadaId);
    
}
