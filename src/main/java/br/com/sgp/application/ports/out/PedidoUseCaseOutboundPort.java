package br.com.sgp.application.ports.out;

import br.com.sgp.application.core.domain.Pedido;
import br.com.sgp.application.core.domain.StatusPedido;
import br.com.sgp.application.core.domain.Temporada;
import br.com.sgp.application.core.exception.NegocioException;

import java.util.Date;
import java.util.List;

public interface PedidoUseCaseOutboundPort {

    Pedido salvar(Pedido pedido) throws NegocioException;
    List<Pedido> buscarTodosPorTemporada(Long idTemporada);
    void excluir(Long id);
    Pedido buscarPeloId(Long id);
    List<Pedido> buscarPedidosDaTemporadaPelaSituacao(Long idTemporada, String situacao);
    List<Pedido> buscarPedidosDaTemporadaPeloStatusPagamento(Long idTemporada, String statusPagamento);
    List<Pedido> buscarPedidosDaTemporadaPeloNomeAluno(Long idTemporada, String nome);
    List<Pedido> buscarPedidosDaTemporadaPelaData(Long idTemporada, Date data);
    List<Pedido> buscarPelaTemporadaAssimComoSituacao(Temporada temporada, StatusPedido situacao);
    Boolean pedidoExiste(Long id);
}
