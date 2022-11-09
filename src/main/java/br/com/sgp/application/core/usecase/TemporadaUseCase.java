package br.com.sgp.application.core.usecase;

import br.com.sgp.application.core.domain.Temporada;
import br.com.sgp.application.core.domain.TipoProduto;
import br.com.sgp.application.core.exception.NegocioException;
import br.com.sgp.application.ports.in.PedidoUseCaseInboundPort;
import br.com.sgp.application.ports.in.TemporadaUseCaseInboundPort;
import br.com.sgp.application.ports.out.TemporadaUseCaseOutboundPort;
import lombok.AllArgsConstructor;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class TemporadaUseCase implements TemporadaUseCaseInboundPort {

    private final TemporadaUseCaseOutboundPort outboundPort;
    private final PedidoUseCaseInboundPort pedidoInboundPort;


    @Override
    public Temporada salvar(Temporada temporada) throws NegocioException {

        if(existeTemporadaAtiva()) {
            throw new NegocioException("Não pode haver duas temporadas ativas ao mesmo tempo! Encerra a atual para abrir uma nova.");
        }
        else {
            temporada.setDataInicio(OffsetDateTime.now());
            return outboundPort.salvar(temporada);
        }
    }

    @Override
    public void excluir(Long id) {

        outboundPort.excluir(id);
    }

    @Override
    public Temporada encerrarTemporadaAtual() {

        var temporada = this.buscarAtiva();
        temporada.setDataFim(OffsetDateTime.now());
        pedidoInboundPort.encerrarTemporadaDePedidos(temporada);
        return outboundPort.salvar(temporada);
    }

    @Override
    public Temporada adicionarProdutos(Temporada temporada, HashMap<TipoProduto, Integer> produtos) {

        for (Map.Entry<TipoProduto, Integer> produto : produtos.entrySet()) {
            if(!temporada.getCatalogo().containsKey(produto.getKey())) {
                temporada.habilitarProduto(produto.getKey(), produto.getValue());
            }
        }
        return temporada;
    }

    @Override
    public Temporada alterarTemporada(Temporada temporadaRequest) {

        var temporada = this.buscarAtiva();
        temporada.setDescricao(temporadaRequest.getDescricao());
        temporada = this.adicionarProdutos(temporada, temporadaRequest.getCatalogo());
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

    @Override
    public boolean existeTemporadaAtiva() {

        return outboundPort.existeTemporadaAtiva();
    }
}
