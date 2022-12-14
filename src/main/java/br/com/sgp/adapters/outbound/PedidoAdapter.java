package br.com.sgp.adapters.outbound;

import br.com.sgp.adapters.inbound.entity.PedidoEntity;
import br.com.sgp.adapters.inbound.entity.TemporadaEntity;
import br.com.sgp.adapters.inbound.mapper.GenericMapper;
import br.com.sgp.adapters.outbound.repository.PedidoRepository;
import br.com.sgp.application.core.domain.Pedido;
import br.com.sgp.application.core.domain.StatusPagamento;
import br.com.sgp.application.core.domain.StatusPedido;
import br.com.sgp.application.core.domain.Temporada;
import br.com.sgp.application.core.exception.EntidadeNaoEncontradaException;
import br.com.sgp.application.core.exception.NegocioException;
import br.com.sgp.application.ports.out.PedidoUseCaseOutboundPort;
import lombok.AllArgsConstructor;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
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
    public List<Pedido> buscarTodosPorTemporada(Long idTemporada) {

        var pedidos = repository.findAllByTemporadaId(idTemporada);
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
    public List<Pedido> buscarPedidosDaTemporadaPelaSituacao(Long idTemporada, String situacao) {

        var pedidos = repository.findBySituacaoAndTemporadaId(StatusPedido.valueOf(situacao.toUpperCase()), idTemporada);
        return mapper.mapToList(pedidos, new TypeToken<List<Pedido>>() {}.getType());
    }

    @Override
    public List<Pedido> buscarPedidosDaTemporadaPeloStatusPagamento(Long idTemporada, String statusPagamento) {

        var pedidos = repository.findByStatusPagamentoAndTemporadaId(StatusPagamento.valueOf(statusPagamento.toUpperCase()), idTemporada);
        return mapper.mapToList(pedidos, new TypeToken<List<Pedido>>() {}.getType());
    }
    public List<Pedido> buscarPedidosDaTemporadaPeloNomeAluno(Long idTemporada, String nome) {

        var pedidos = repository.findByAlunoNomeAndTemporadaId(nome, idTemporada);
        return mapper.mapToList(pedidos, new TypeToken<List<Pedido>>() {}.getType());
    }
    public List<Pedido> buscarPedidosDaTemporadaPelaData(Long idTemporada, Date data) {

        OffsetDateTime startDay = data.toInstant().atOffset(ZoneOffset.ofHours(-3)).plusHours(3);
        OffsetDateTime endDay = startDay
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999000).toInstant().atOffset(ZoneOffset.ofHours(-3));
        var pedidos = repository.findByDataBetweenAndTemporadaId(startDay, endDay, idTemporada);
        return mapper.mapToList(pedidos, new TypeToken<List<Pedido>>() {}.getType());
    }
    public List<Pedido> buscarPelaTemporadaAssimComoSituacao(Temporada temporada, StatusPedido situacao) {

        var temporadaEntity = mapper.mapTo(temporada, TemporadaEntity.class);
        var pedidos = repository.findByTemporadaAndSituacao(temporadaEntity, situacao);
        return mapper.mapToList(pedidos, new TypeToken<List<Pedido>>() {}.getType());
    }

    public Boolean pedidoExiste(Long id) {

        return repository.existsById(id);
    }
}
