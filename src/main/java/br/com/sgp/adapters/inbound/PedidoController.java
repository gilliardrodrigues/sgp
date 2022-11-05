package br.com.sgp.adapters.inbound;

import br.com.sgp.adapters.inbound.mapper.GenericMapper;
import br.com.sgp.adapters.inbound.request.PedidoRequest;
import br.com.sgp.adapters.inbound.request.ProdutoRequest;
import br.com.sgp.adapters.inbound.response.PedidoResponse;
import br.com.sgp.application.core.domain.*;
import br.com.sgp.application.ports.in.PedidoUseCaseInboundPort;
import br.com.sgp.application.ports.in.ProdutoUseCaseInboundPort;
import br.com.sgp.application.ports.in.TemporadaUseCaseInboundPort;
import lombok.AllArgsConstructor;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@AllArgsConstructor
public class PedidoController {
    private final PedidoUseCaseInboundPort inboundPort;
    private final TemporadaUseCaseInboundPort temporadaInboundPort;
    private final ProdutoUseCaseInboundPort produtoInboundPort;
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

        var pedido = new Pedido();
        pedido.setAluno(pedidoRequest.getAluno());

        List<Produto> produtos = new ArrayList<Produto>();
        for (ProdutoRequest produtoRequest : pedidoRequest.getProdutos()) {
            if (produtoRequest.getTipo().equals(TipoProduto.CAMISA)) {
                produtos.add(mapper.mapTo(produtoRequest, Camisa.class));
            } else if (produtoRequest.getTipo().equals(TipoProduto.CANECA)) {
                produtos.add(mapper.mapTo(produtoRequest, Caneca.class));
            } else {
                produtos.add(mapper.mapTo(produtoRequest, Tirante.class));
            }
        }

        var pedidoSalvo = inboundPort.salvar(pedido, produtos);

        try {
            return ResponseEntity.ok(mapper.mapTo(pedidoSalvo, PedidoResponse.class));
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
