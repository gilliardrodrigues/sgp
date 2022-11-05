package br.com.sgp.application.core.usecase;

import br.com.sgp.application.core.domain.Temporada;
import br.com.sgp.application.core.domain.TipoProduto;
import br.com.sgp.application.core.exception.NegocioException;
import br.com.sgp.application.ports.in.TemporadaUseCaseInboundPort;
import br.com.sgp.application.ports.out.TemporadaUseCaseOutboundPort;
import br.com.sgp.application.ports.out.PedidoUseCaseOutboundPort;
import br.com.sgp.application.core.domain.StatusPedido;
import br.com.sgp.application.core.domain.Pedido;

import java.time.OffsetDateTime;
import java.util.List;

public class TemporadaUseCase implements TemporadaUseCaseInboundPort {

    private final TemporadaUseCaseOutboundPort outboundPort;
    private final PedidoUseCaseOutboundPort pedidoOutboundPort;

    public TemporadaUseCase(TemporadaUseCaseOutboundPort outboundPort, PedidoUseCaseOutboundPort pedidoOutboundPort) {

        this.outboundPort = outboundPort;
        this.pedidoOutboundPort = pedidoOutboundPort;
    }

    @Override
    public Temporada salvar(Temporada temporada) throws NegocioException {

        temporada.setDataInicio(OffsetDateTime.now());
        return outboundPort.salvar(temporada);
    }

    @Override
    public void excluir(Long id) {

        outboundPort.excluir(id);
    }

    @Override
    public Temporada encerrarTemporada(Long id) {

        var temporada = this.buscarPeloId(id);
        temporada.setDataFim(OffsetDateTime.now());


        // pedidoOutboundPort.excluirNaoConfirmados();

        List<Pedido> pedidosNaoConfirmados = pedidoOutboundPort.buscarPelaSituacao(StatusPedido.AGUARDANDO_PAGAMENTO.name());
        pedidosNaoConfirmados.forEach(pedido -> {
            pedidoOutboundPort.excluir(pedido.getId());
        });

        // TODO setar data de entrega dos pedidos e produtos
        // pedidoOutboundPort.setarPrevisaoDeEntrega();

        // List<Pedido> pedidosNaoConfirmados = pedidoOutboundPort.buscarPelaSituacao(StatusPedido.AGUARDANDO_PAGAMENTO.name());
        // pedidosNaoConfirmados.forEach(pedido -> {
        //     pedidoOutboundPort.excluir(pedido.getId());
        // });

        // A lógica da contabilização de pedidos (apenas com pagamento confirmado) deve ser
        // tratada no caso de uso de pedido (só associar à temporada se tiver confirmado o pagamento)
        return outboundPort.salvar(temporada);
    }

    @Override
    public Temporada adicionarProdutos(Temporada temporada, List<TipoProduto> produtos) {

        for (TipoProduto produto : produtos) {
            if(!temporada.getProdutosDisponiveis().contains(produto)) {
                temporada.habilitarProduto(produto);
            }
        }
        return temporada;
    }

    @Override
    public Temporada alterarTemporada(Temporada temporadaRequest) {

        var temporada = this.buscarPeloId(temporadaRequest.getId());
        temporada.setDescricao(temporadaRequest.getDescricao());
        temporada = this.adicionarProdutos(temporada, temporadaRequest.getProdutosDisponiveis());
        return outboundPort.salvar(temporada);
    }

    @Override
    public Temporada buscarPeloId(Long id) {

        return outboundPort.buscarPeloId(id);
    }

    @Override
    public List<Temporada> buscarTodas() {

        return outboundPort.buscarTodas();
    }

    @Override
    public boolean temporadaExiste(Long id) {

        return outboundPort.temporadaExiste(id);
    }

    @Override
    public Temporada buscarAtiva() {

        return outboundPort.buscarAtiva();
    }

}
