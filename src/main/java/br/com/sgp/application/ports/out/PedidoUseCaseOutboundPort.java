package br.com.sgp.application.ports.out;

import br.com.sgp.application.core.domain.Pedido;
import br.com.sgp.application.core.domain.StatusPedido;
import br.com.sgp.application.core.domain.Temporada;
import br.com.sgp.application.core.exception.NegocioException;

import java.util.List;

public interface PedidoUseCaseOutboundPort {

    Pedido salvar(Pedido pedido) throws NegocioException;
    List<Pedido> buscarTodos();
    void excluir(Long id);
    Pedido buscarPeloId(Long id);
    List<Pedido> buscarPelaSituacao(String situacao);
    List<Pedido> buscarPeloStatusPagamento(String statusPagamento);

    List<Pedido> buscarPelaTemporadaAssimComoSituacao(Temporada temporada, StatusPedido situacao);
    Boolean pedidoExiste(Long id);
}
