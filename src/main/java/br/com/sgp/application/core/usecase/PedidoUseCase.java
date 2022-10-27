package br.com.sgp.application.core.usecase;

import br.com.sgp.adapters.inbound.mapper.GenericMapper;
import br.com.sgp.application.core.domain.Pedido;
import br.com.sgp.application.core.domain.StatusPagamento;
import br.com.sgp.application.core.domain.StatusPedido;
import br.com.sgp.application.core.domain.Temporada;
import br.com.sgp.application.core.exception.NegocioException;
import br.com.sgp.application.ports.in.PedidoUseCaseInboundPort;
import br.com.sgp.application.ports.out.PedidoUseCaseOutboundPort;
import br.com.sgp.application.ports.out.ProdutoUseCaseOutboundPort;

import java.time.OffsetDateTime;
import java.util.List;

public class PedidoUseCase implements PedidoUseCaseInboundPort {

    private final PedidoUseCaseOutboundPort outboundPort;
    private final ProdutoUseCaseOutboundPort produtoOutboundPort;
    private GenericMapper mapper;

    public PedidoUseCase(PedidoUseCaseOutboundPort outboundPort, ProdutoUseCaseOutboundPort produtoOutboundPort) {

        this.outboundPort = outboundPort;
        this.produtoOutboundPort = produtoOutboundPort;
    }

    @Override
    public Pedido salvar(Pedido pedido) throws NegocioException {

        pedido.setData(OffsetDateTime.now());
        pedido.setSituacao(StatusPedido.AGUARDANDO_PAGAMENTO);
        pedido.setStatusPagamento(StatusPagamento.NAO_PAGO);
        // CONTINUAR SETTANDO AS OUTRAS COISAS, COMO A TEMPORADA E OS PRODUTOS.
        return outboundPort.salvar(pedido);
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
    public List<Pedido> buscarPelaSituacao(String situacao) {

        return outboundPort.buscarPelaSituacao(situacao);
    }

    @Override
    public List<Pedido> buscarPeloStatusPagamento(String statusPagamento) {

        return outboundPort.buscarPeloStatusPagamento(statusPagamento);
    }

    @Override
    public void adicionarProdutoDoInventario(Pedido pedido, Long idProduto) {

        //TODO
    }

    @Override
    public List<Pedido> buscarPedidosConfirmadosPorTemporada(Temporada temporada) {

        return outboundPort.buscarPelaTemporadaAssimComoSituacao(temporada, StatusPedido.CONFIRMADO);
    }
}