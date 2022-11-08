package br.com.sgp.application.ports.in;

import br.com.sgp.application.core.domain.Pedido;
import br.com.sgp.application.core.domain.Temporada;
import br.com.sgp.application.core.domain.TipoProduto;
import br.com.sgp.application.core.exception.NegocioException;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface PedidoUseCaseInboundPort {

    Pedido salvar(Pedido pedido) throws NegocioException;
    List<Pedido> buscarTodos();
    void excluir(Long id);
    Pedido buscarPeloId(Long id);
    List<Pedido> buscarPedidos(String situacao, String statusPagamento, String nome, Date data,  String tipoDeProduto) ;
    List<Pedido> buscarPedidosConfirmadosPorTemporada(Temporada temporada);
    Set<Pedido> buscarPeloTipoDeProduto(TipoProduto tipoProduto);
    Boolean pedidoExiste(Long id);
    void encerrarTemporadaDePedidos(Temporada temporada);
    Pedido darBaixa(Long id, int valorPago) throws NegocioException;
    // void adicionarProduto(Long idPedido, Produto produto);
}
