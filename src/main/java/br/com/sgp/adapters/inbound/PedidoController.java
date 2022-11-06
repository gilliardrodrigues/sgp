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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
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
    public ResponseEntity<List<PedidoResponse>> buscarPedidos(@RequestParam(required = false) String situacao, @RequestParam(required = false) String statusPagamento,
            @RequestParam(required = false) String nome, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data,
            @RequestParam(required = false) String tipoDeProduto) {
        List<Pedido> pedidos = inboundPort.buscarPedidos(situacao, statusPagamento, nome, data, tipoDeProduto);
        return ResponseEntity.ok(mapper.mapToList(pedidos, new TypeToken<List<PedidoResponse>>() {}.getType()));
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

        var pedidoSalvo = inboundPort.salvar(pedido);

        try {
            return ResponseEntity.ok(mapper.mapTo(pedidoSalvo, PedidoResponse.class));
        } catch (Throwable e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<PedidoResponse> alterarPedido(@Valid @PathVariable Long id,
            @RequestBody PedidoRequest pedidoRequest) {
        try {
            Pedido pedido = inboundPort.darBaixa(id, pedidoRequest.getValorPago());
            return ResponseEntity.ok(mapper.mapTo(inboundPort.salvar(pedido), PedidoResponse.class));
        } catch (Throwable e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<PedidoResponse> excluir(@PathVariable Long id) {

        if (!inboundPort.pedidoExiste(id))
            return ResponseEntity.notFound().build();

        inboundPort.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
