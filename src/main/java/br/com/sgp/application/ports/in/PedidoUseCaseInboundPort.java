package br.com.sgp.application.ports.in;

import br.com.sgp.application.core.domain.Pedido;
import br.com.sgp.application.core.domain.Temporada;
import br.com.sgp.application.core.exception.NegocioException;
import br.com.sgp.application.core.domain.Produto;

import java.util.List;

public interface PedidoUseCaseInboundPort {

    Pedido salvar(Pedido pedido, List<Produto> produtos) throws NegocioException;
    Pedido salvar(Pedido pedido) throws NegocioException;
    List<Pedido> buscarTodos();
    void excluir(Long id);
    Pedido buscarPeloId(Long id);
    List<Pedido> buscarPelaSituacao(String situacao);
    List<Pedido> buscarPeloStatusPagamento(String statusPagamento);
    List<Pedido> buscarPedidosConfirmadosPorTemporada(Temporada temporada);
    void adicionarProdutoDoInventario(Pedido pedido, Long idProduto);
    Boolean pedidoExiste(Long id);
    void adicionarProduto(Long idPedido, Produto produto);
}
