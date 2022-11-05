package br.com.sgp.adapters.inbound;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import br.com.sgp.adapters.inbound.mapper.GenericMapper;
import br.com.sgp.adapters.inbound.request.PedidoRequest;
import br.com.sgp.adapters.inbound.response.PedidoResponse;
import br.com.sgp.adapters.inbound.response.TemporadaResponse;
import br.com.sgp.application.core.domain.Pedido;
import br.com.sgp.application.ports.in.PedidoUseCaseInboundPort;
import lombok.AllArgsConstructor;
import org.modelmapper.TypeToken;

import br.com.sgp.application.core.domain.*;
import br.com.sgp.application.ports.in.TemporadaUseCaseInboundPort;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/pedidos")
@AllArgsConstructor
public class PedidoController {
    private final PedidoUseCaseInboundPort inboundPort;
    private final TemporadaUseCaseInboundPort temporadaInboundPort;
    private final GenericMapper mapper;

    @GetMapping("/admin")
    public ResponseEntity<List<PedidoResponse>> listarPedidos() {

        var pedidos = inboundPort.buscarTodos();
        return ResponseEntity.ok(mapper.mapToList(pedidos, new TypeToken<List<PedidoResponse>>() {
        }.getType()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> obterPedido(@PathVariable Long id) {

        try {
            var pedido = inboundPort.buscarPeloId(id);
            return ResponseEntity.ok(mapper.mapTo(pedido, PedidoResponse.class));
        } catch (Throwable e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<PedidoResponse> salvarPedido(@Valid @RequestBody PedidoRequest pedidoRequest) {

        var pedidoToSave = mapper.mapTo(pedidoRequest, Pedido.class);

        Temporada temporada = temporadaInboundPort.buscarAtiva();
        pedidoToSave.setTemporada(temporada);

        var pedido = inboundPort.salvar(pedidoToSave);

        // pedido.getProdutos();

        try {
            return ResponseEntity.ok(mapper.mapTo(pedido, PedidoResponse.class));
        } catch (Throwable e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<PedidoResponse> alterarPedido(@Valid @PathVariable Long id,
            @RequestBody PedidoRequest pedidoRequest) {

        if (!inboundPort.pedidoExiste(id))
            return ResponseEntity.notFound().build();

        var pedido = inboundPort.buscarPeloId(id);

        if (pedidoRequest.getValorPago() != null)
            pedido.setValorPago(pedidoRequest.getValorPago());

        return ResponseEntity.ok(mapper.mapTo(inboundPort.salvar(pedido), PedidoResponse.class));
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<PedidoResponse> excluir(@PathVariable Long id) {

        if (!inboundPort.pedidoExiste(id))
            return ResponseEntity.notFound().build();

        inboundPort.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
