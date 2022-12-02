package br.com.sgp.application.core.usecase;

import br.com.sgp.application.core.domain.*;
import br.com.sgp.application.core.exception.EntidadeNaoEncontradaException;
import br.com.sgp.application.core.exception.NegocioException;
import br.com.sgp.application.ports.in.PedidoUseCaseInboundPort;
import br.com.sgp.application.ports.out.FornecedorUseCaseOutboundPort;
import br.com.sgp.application.ports.out.PedidoUseCaseOutboundPort;
import br.com.sgp.application.ports.out.ProdutoUseCaseOutboundPort;
import br.com.sgp.application.ports.out.TemporadaUseCaseOutboundPort;
import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class PedidoUseCase implements PedidoUseCaseInboundPort {

    private final PedidoUseCaseOutboundPort outboundPort;
    private final ProdutoUseCaseOutboundPort produtoOutboundPort;
    private final TemporadaUseCaseOutboundPort temporadaOutboundPort;
    private final FornecedorUseCaseOutboundPort fornecedorOutboundPort;
    
    @Override
    public Pedido salvar(Pedido pedido) throws NegocioException {

        if(!temporadaOutboundPort.existeTemporadaAtiva()) {
            throw new NegocioException("Não foi possível criar o pedido, pois não há temporada ativa no momento!");
        }
        else {
            Temporada temporada = temporadaOutboundPort.buscarAtiva();
            pedido.setTemporada(temporada);
            var pedidoSalvo = outboundPort.salvar(pedido);

            return outboundPort.buscarPeloId(pedidoSalvo.getId());
        }
    }

    @Override
    public List<Pedido> buscarTodosPorTemporada(Long idTemporada) {

        return outboundPort.buscarTodosPorTemporada(idTemporada);
    }

    @Override
    public void excluir(Long id) {

        Pedido pedido;
        try {
            pedido = buscarPeloId(id);
        } catch (Throwable e) {
            throw new EntidadeNaoEncontradaException(e.getMessage());
        }
        if(!pedido.getStatusPagamento().equals(StatusPagamento.NAO_PAGO)) {
            throw new NegocioException("Não foi possível excluir o pedido, pois ele já foi pago!");
        }
        outboundPort.excluir(id);
    }

    @Override
    public Pedido buscarPeloId(Long id) {

        var pedido = outboundPort.buscarPeloId(id);
        pedido = validarStatusPedido(pedido.getId());
        return pedido;
    }

    @Override
    public List<Pedido> buscarPedidos(String situacao, String statusPagamento, String nome, Date data,
                                      String tipoDeProduto, Long idTemporada) {
        if(situacao != null && !situacao.isEmpty()) {
            return outboundPort.buscarPedidosDaTemporadaPelaSituacao(idTemporada, situacao);
        } else if(statusPagamento != null && !statusPagamento.isEmpty()) {
            return outboundPort.buscarPedidosDaTemporadaPeloStatusPagamento(idTemporada, statusPagamento);
        } else if(nome != null && !nome.isEmpty()) {
            return outboundPort.buscarPedidosDaTemporadaPeloNomeAluno(idTemporada, nome);
        } else if(data != null) {
            return outboundPort.buscarPedidosDaTemporadaPelaData(idTemporada, data);
        } else if(tipoDeProduto != null && !tipoDeProduto.isEmpty()) {
            var pedidos = buscarPedidosDaTemporadaPeloTipoDeProduto(idTemporada, TipoProduto.valueOf(tipoDeProduto));
            return new ArrayList<>(pedidos);
        } else {
            return buscarTodosPorTemporada(idTemporada);
        }
    }
    @Override
    public Set<Pedido> buscarPedidosDaTemporadaPeloTipoDeProduto(Long idTemporada, TipoProduto tipoProduto) {

        var pedidos = new HashSet<Pedido>();
        List<Produto> produtosDoTipo;

        if(tipoProduto.equals(TipoProduto.CAMISA)) {
            produtosDoTipo = new ArrayList<>(produtoOutboundPort.buscarTodasCamisas());
        }
        else if(tipoProduto.equals(TipoProduto.CANECA)) {
            produtosDoTipo = new ArrayList<>(produtoOutboundPort.buscarTodasCanecas());
        }
        else {
            produtosDoTipo = new ArrayList<>(produtoOutboundPort.buscarTodosTirantes());
        }
        for(Produto produto : produtosDoTipo) {
            var pedido = produto.getPedido();
            if(pedido != null) {
                var temporada = pedido.getTemporada();
                if(temporada.getId().equals(idTemporada)) {
                    pedidos.add(produto.getPedido());
                }
            }
        }
        return pedidos;
    }

    @Override
    public Pedido darBaixa(Long id, int valorPago) throws NegocioException {
        if (!outboundPort.pedidoExiste(id))
            throw new NegocioException("Pedido não encontrado");
        
        var pedido = outboundPort.buscarPeloId(id);

        if (valorPago < 0 || valorPago > pedido.getValor())
            throw new NegocioException("Valor pago inválido");
        
        pedido.setValorPago(valorPago);

        return outboundPort.salvar(pedido);
    }
    @Override
    public Pedido validarStatusPedido(Long idPedido) {

        var produtos = produtoOutboundPort.buscarPeloIdPedido(idPedido);
        var pedido = outboundPort.buscarPeloId(idPedido);
        var situacao = pedido.getSituacao();
        if(!(situacao.equals(StatusPedido.PARCIALMENTE_ENTREGUE) || situacao.equals(StatusPedido.ENTREGUE))) {
            if(produtos.stream().allMatch(Produto::getChegou)) {
                pedido.setSituacao(StatusPedido.PRONTO_PARA_ENTREGA);
                return outboundPort.salvar(pedido);
            }
        }
        else {
            if(produtos.stream().allMatch(Produto::getEntregue)) {
                pedido.setSituacao(StatusPedido.ENTREGUE);
                return outboundPort.salvar(pedido);
            }
        }
        return pedido;
    }

    @Override
    public List<Pedido> buscarPedidosConfirmadosPorTemporada(Temporada temporada) {

        return outboundPort.buscarPelaTemporadaAssimComoSituacao(temporada, StatusPedido.CONFIRMADO);
    }

    public Boolean pedidoExiste(Long id) {

        return outboundPort.pedidoExiste(id);
    }

    @Override
    public void encerrarTemporadaDePedidos(Temporada temporada) {
         List<Pedido> pedidosNaoConfirmados = outboundPort.buscarPelaTemporadaAssimComoSituacao(temporada, StatusPedido.AGUARDANDO_PAGAMENTO);
         pedidosNaoConfirmados.forEach(pedido -> outboundPort.excluir(pedido.getId()));

         List<Pedido> pedidosConfirmados = outboundPort.buscarPelaTemporadaAssimComoSituacao(temporada, StatusPedido.CONFIRMADO);
         List<Fornecedor> fornecedores = fornecedorOutboundPort.buscarTodos();
         pedidosConfirmados.forEach(pedido -> {
             List<Camisa> camisas = produtoOutboundPort.buscarCamisasPeloIdPedido(pedido.getId());
             camisas.forEach(camisa -> {
                 var previsaoDeEntrega = camisa.calcularPrevisaoDeEntrega(fornecedores);
                 pedido.addPrevisaoDeEntrega(previsaoDeEntrega);
                 camisa.setPrevisaoDeEntrega(previsaoDeEntrega);
                 produtoOutboundPort.salvarCamisa(camisa);
             });
             List<Caneca> canecas = produtoOutboundPort.buscarCanecasPeloIdPedido(pedido.getId());
             canecas.forEach(caneca -> {
                 var previsaoDeEntrega = caneca.calcularPrevisaoDeEntrega(fornecedores);
                 pedido.addPrevisaoDeEntrega(previsaoDeEntrega);
                 caneca.setPrevisaoDeEntrega(previsaoDeEntrega);
                 produtoOutboundPort.salvarCaneca(caneca);
             });
             List<Tirante> tirantes = produtoOutboundPort.buscarTirantesPeloIdPedido(pedido.getId());
             tirantes.forEach(tirante -> {
                 var previsaoDeEntrega = tirante.calcularPrevisaoDeEntrega(fornecedores);
                 pedido.addPrevisaoDeEntrega(previsaoDeEntrega);
                 tirante.setPrevisaoDeEntrega(previsaoDeEntrega);
                 produtoOutboundPort.salvarTirante(tirante);
             });
             outboundPort.salvar(pedido);
         });
    }
}
