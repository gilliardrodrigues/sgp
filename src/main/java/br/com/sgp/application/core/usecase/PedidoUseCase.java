package br.com.sgp.application.core.usecase;

import br.com.sgp.application.core.domain.*;
import br.com.sgp.application.core.exception.NegocioException;
import br.com.sgp.application.ports.in.PedidoUseCaseInboundPort;
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
    
    @Override
    public Pedido salvar(Pedido pedido) throws NegocioException {
        Temporada temporada = temporadaOutboundPort.buscarAtiva();
            
        pedido.setTemporada(temporada);
        var pedidoSalvo = outboundPort.salvar(pedido);

        return outboundPort.buscarPeloId(pedidoSalvo.getId());
    }

    @Override
    public List<Pedido> buscarTodos() {

        return outboundPort.buscarTodos();
    }

    @Override
    public void excluir(Long id) {


        outboundPort.excluir(id);
    }

    @Override
    public Pedido buscarPeloId(Long id) {

        return outboundPort.buscarPeloId(id);
    }

    @Override
    public List<Pedido> buscarPedidos(String situacao, String statusPagamento, String nome, Date data, String tipoDeProduto) {
        if(situacao != null && !situacao.isEmpty()) {
            return outboundPort.buscarPelaSituacao(situacao);
        } else if(statusPagamento != null && !statusPagamento.isEmpty()) {
            return outboundPort.buscarPeloStatusPagamento(statusPagamento);
        } else if(nome != null && !nome.isEmpty()) {
            return outboundPort.buscarPeloNomeAluno(nome);
        } else if(data != null) {
            return outboundPort.buscarPelaData(data);
        } else if(tipoDeProduto != null && !tipoDeProduto.isEmpty()) {
            var pedidos = buscarPeloTipoDeProduto(TipoProduto.valueOf(tipoDeProduto));
            return new ArrayList<>(pedidos);
        } else {
            return outboundPort.buscarTodos();
        }
    }
    @Override
    public Set<Pedido> buscarPeloTipoDeProduto(TipoProduto tipoProduto) {

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
            if(produto.getPedido() != null) {
                pedidos.add(produto.getPedido());
            }
        }
        return pedidos;
    }

    @Override
    public Pedido darBaixa(Long id, int valorPago) throws NegocioException {
        if (!outboundPort.pedidoExiste(id)) throw new NegocioException("Pedido não encontrado");
        
        var pedido = outboundPort.buscarPeloId(id);

        if (valorPago < 0 || valorPago > pedido.getValor())
            throw new NegocioException("Valor pago inválido");
        
        pedido.setValorPago(valorPago);

        return outboundPort.salvar(pedido);
    }

    @Override
    public List<Pedido> buscarPedidosConfirmadosPorTemporada(Temporada temporada) {

        return outboundPort.buscarPelaTemporadaAssimComoSituacao(temporada, StatusPedido.CONFIRMADO);
    }

    public Boolean pedidoExiste(Long id) {

        return outboundPort.pedidoExiste(id);
    }

    @Override
    public void encerrarTemporadaDePedidos(Temporada temporada) { //TODO gill
    //     List<Pedido> pedidosNaoConfirmados = outboundPort.buscarPelaTemporadaAssimComoSituacao(temporada, StatusPedido.AGUARDANDO_PAGAMENTO);
    //     pedidosNaoConfirmados.forEach(pedido -> {
    //         outboundPort.excluir(pedido.getId());
    //     });


    //     List<Pedido> pedidosConfirmados = outboundPort.buscarPelaTemporadaAssimComoSituacao(temporada, StatusPedido.CONFIRMADO);
    //     List<Fornecedor> fornecedores = fornecedorOutboundPort.buscarTodos();
    //     pedidosConfirmados.forEach(pedido -> {

    //         List<Produto> produtos = produtoInboundPort.buscarPeloIdPedido(pedido.getId());

    //         produtos.forEach(produto -> {

    //             var previsaoDeEntrega = produto.calcularPrevisaoDeEntrega(fornecedores);
    //             pedido.addPrevisaoDeEntrega(previsaoDeEntrega);
    //             produto.setPrevisaoDeEntrega(previsaoDeEntrega);
    //             produtoInboundPort.salvar(produto);
    //         });

    //         outboundPort.salvar(pedido);
    //     });
    }
}
