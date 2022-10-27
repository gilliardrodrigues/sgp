package br.com.sgp.adapters.outbound;

import br.com.sgp.adapters.inbound.entity.PedidoEntity;
import br.com.sgp.adapters.inbound.entity.TemporadaEntity;
import br.com.sgp.adapters.inbound.mapper.GenericMapper;
import br.com.sgp.adapters.outbound.repository.PedidoRepository;
import br.com.sgp.application.core.domain.*;
import br.com.sgp.application.core.exception.EntidadeNaoEncontradaException;
import br.com.sgp.application.core.exception.NegocioException;
import br.com.sgp.application.ports.out.PedidoUseCaseOutboundPort;
import lombok.AllArgsConstructor;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@AllArgsConstructor
public class PedidoAdapter implements PedidoUseCaseOutboundPort {

    private final PedidoRepository repository;
    private final GenericMapper mapper;

    @Override
    @Transactional
    public Pedido salvar(Pedido pedido) throws NegocioException {

        var pedidoEntity = mapper.mapTo(pedido, PedidoEntity.class);
        var pedidoSalvo = repository.save(pedidoEntity);
        return mapper.mapTo(pedidoSalvo, Pedido.class);
    }

    @Override
    public List<Pedido> buscarTodos() {

        var pedidos = repository.findAll();
        return mapper.mapToList(pedidos, new TypeToken<List<Pedido>>() {}.getType());
    }

    @Override
    @Transactional
    public void excluir(Long id) {

        repository.deleteById(id);
    }

    @Override
    public Pedido buscarPeloId(Long id) {


        var pedidoEntity = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pedido não encontrado!"));
        return mapper.mapTo(pedidoEntity, Pedido.class);
    }

    @Override
    public List<Pedido> buscarPelaSituacao(String situacao) {

        var pedidos = repository.findBySituacao(StatusPedido.valueOf(situacao.toUpperCase()));
        return mapper.mapToList(pedidos, new TypeToken<List<Pedido>>() {}.getType());
    }

    @Override
    public List<Pedido> buscarPeloStatusPagamento(String statusPagamento) {

        var pedidos = repository.findByStatusPagamento(StatusPagamento.valueOf(statusPagamento.toUpperCase()));
        return mapper.mapToList(pedidos, new TypeToken<List<Pedido>>() {}.getType());
    }

    public List<Pedido> buscarPelaTemporadaAssimComoSituacao(Temporada temporada, StatusPedido situacao) {

        var temporadaEntity = mapper.mapTo(temporada, TemporadaEntity.class);
        var pedidos = repository.findByTemporadaAndSituacao(temporadaEntity, situacao);
        return mapper.mapToList(pedidos, new TypeToken<List<Pedido>>() {}.getType());
    }
}