package br.com.sgp.adapters.inbound;

import br.com.sgp.adapters.inbound.mapper.GenericMapper;
import br.com.sgp.adapters.inbound.request.CamisaRequest;
import br.com.sgp.adapters.inbound.request.CanecaRequest;
import br.com.sgp.adapters.inbound.request.IdPedidoRequest;
import br.com.sgp.adapters.inbound.request.TiranteRequest;
import br.com.sgp.adapters.inbound.response.*;
import br.com.sgp.application.core.domain.Camisa;
import br.com.sgp.application.core.domain.Caneca;
import br.com.sgp.application.core.domain.Tirante;
import br.com.sgp.application.core.exception.NegocioException;
import br.com.sgp.application.ports.in.PedidoUseCaseInboundPort;
import br.com.sgp.application.ports.in.ProdutoUseCaseInboundPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/produtos")
@AllArgsConstructor
public class ProdutoController {

    private final ProdutoUseCaseInboundPort inboundPort;
    private final PedidoUseCaseInboundPort pedidoInboundPort;
    private final GenericMapper mapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String listarProdutos() {

        try {
            return inboundPort.buscarTodos();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/camisas")
    public ResponseEntity<List<CamisaResponse>> listarCamisas(@RequestParam(required = false) String cor,
            @RequestParam(required = false) String tamanho, @RequestParam(required = false) String curso) {

        List<Camisa> camisas = inboundPort.buscarCamisas(cor, tamanho, curso);
        return ResponseEntity.ok(mapper.mapToList(camisas, new TypeToken<List<CamisaResponse>>() {}.getType()));
    }

    @GetMapping("/canecas")
    public List<CanecaResponse> listarCanecas() {

        var canecas = inboundPort.buscarTodasCanecas();
        return mapper.mapToList(canecas, new TypeToken<List<CanecaResponse>>() {}.getType());
    }

    @GetMapping("/tirantes")
    public List<TiranteResponse> listarTirantes() {

        var tirantes = inboundPort.buscarTodosTirantes();
        return mapper.mapToList(tirantes, new TypeToken<List<TiranteResponse>>() {}.getType());
    }

    @GetMapping(value = "/inventario", produces = MediaType.APPLICATION_JSON_VALUE)
    public String listarInventario() {

        try {
            return inboundPort.buscarInventario();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarPeloId(@PathVariable Long id) {

        try {
            var produto = inboundPort.buscarPeloId(id);
            if(produto instanceof Camisa) {
                return ResponseEntity.ok(mapper.mapTo(produto, CamisaResponse.class));
            }
            else if(produto instanceof Caneca) {
                return ResponseEntity.ok(mapper.mapTo(produto, CanecaResponse.class));
            }
            else {
                return ResponseEntity.ok(mapper.mapTo(produto, TiranteResponse.class));
            }
        } catch (Throwable e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filtro/pedido/{idPedido}")
    public List<ProdutoResponse> buscarPeloIdPedido(@PathVariable Long idPedido) {

        var produtos = inboundPort.buscarPeloIdPedido(idPedido);
        return mapper.mapToList(produtos, new TypeToken<List<ProdutoResponse>>() {}.getType());
    }

    @GetMapping("/tirantes/filtro/{modelo}")
    public ResponseEntity<List<TiranteResponse>> pesquisarTirantePorModelo(
            @PathVariable(required = false) String modelo) {

        List<Tirante> tirantes = inboundPort.buscarTirantePeloModelo(modelo);
        return ResponseEntity.ok(mapper.mapToList(tirantes, new TypeToken<List<TiranteResponse>>() {}.getType()));
    }

    @GetMapping("/canecas/filtro/{modelo}")
    public ResponseEntity<List<CanecaResponse>> pesquisarCanecaPorModelo(
            @PathVariable(required = false) String modelo) {

        List<Caneca> canecas = inboundPort.buscarCanecaPeloModelo(modelo);
        return ResponseEntity.ok(mapper.mapToList(canecas, new TypeToken<List<CanecaResponse>>() {}.getType()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/camisas")
    public ResponseEntity<CamisaResponse> salvarCamisaNoInventario(@Valid @RequestBody CamisaRequest camisaRequest) throws NegocioException {

        var camisa = mapper.mapTo(camisaRequest, Camisa.class);
        return ResponseEntity.ok(mapper.mapTo(inboundPort.salvarInventario(camisa), CamisaResponse.class));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/canecas")
    public ResponseEntity<CanecaResponse> salvarCanecaNoInventario(@Valid @RequestBody CanecaRequest canecaRequest) throws NegocioException {

        var caneca = mapper.mapTo(canecaRequest, Caneca.class);
        return ResponseEntity.ok(mapper.mapTo(inboundPort.salvarInventario(caneca), CanecaResponse.class));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/tirantes")
    public ResponseEntity<TiranteResponse> salvarTiranteNoInventario(@Valid @RequestBody TiranteRequest tiranteRequest) throws NegocioException {

        var tirante = mapper.mapTo(tiranteRequest, Tirante.class);
        return ResponseEntity.ok(mapper.mapTo(inboundPort.salvarInventario(tirante), TiranteResponse.class));
    }

    @PutMapping("/admin/camisas/{id}")
    public ResponseEntity<CamisaResponse> alterarCamisa(@Valid @PathVariable Long id,
            @RequestBody CamisaRequest camisaRequest) throws NegocioException {

        var camisa = mapper.mapTo(camisaRequest, Camisa.class);
        camisa.setId(id);
        return inboundPort.produtoExiste(id)
                ? ResponseEntity.ok(mapper.mapTo(inboundPort.salvarInventario(camisa), CamisaResponse.class))
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/admin/canecas/{id}")
    public ResponseEntity<CanecaResponse> alterarCaneca(@Valid @PathVariable Long id,
            @RequestBody CanecaRequest canecaRequest) throws NegocioException {

        var caneca = mapper.mapTo(canecaRequest, Caneca.class);
        caneca.setId(id);
        return inboundPort.produtoExiste(id)
                ? ResponseEntity.ok(mapper.mapTo(inboundPort.salvarInventario(caneca), CanecaResponse.class))
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/admin/tirantes/{id}")
    public ResponseEntity<TiranteResponse> alterarTirante(@Valid @PathVariable Long id,
            @RequestBody TiranteRequest tiranteRequest) throws NegocioException {

        var tirante = mapper.mapTo(tiranteRequest, Tirante.class);
        tirante.setId(id);
        return inboundPort.produtoExiste(id)
                ? ResponseEntity.ok(mapper.mapTo(inboundPort.salvarInventario(tirante), TiranteResponse.class))
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<ProdutoResponse> excluir(@PathVariable Long id) {

        if (!inboundPort.produtoExiste(id))
            return ResponseEntity.notFound().build();

        inboundPort.excluir(id);
        return ResponseEntity.noContent().build();
    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/camisas")
    public ResponseEntity<CamisaResponse> pedirCamisa(@Valid @RequestBody CamisaRequest camisaRequest) throws NegocioException {
        camisaRequest.setValor(null);
        var camisa = mapper.mapTo(camisaRequest, Camisa.class);
        return ResponseEntity.ok(mapper.mapTo(inboundPort.salvar(camisa), CamisaResponse.class));
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/canecas")
    public ResponseEntity<CanecaResponse> pedirCaneca(@Valid @RequestBody CanecaRequest canecaRequest) throws NegocioException {
        canecaRequest.setValor(null);
        var caneca = mapper.mapTo(canecaRequest, Caneca.class);
        return ResponseEntity.ok(mapper.mapTo(inboundPort.salvar(caneca), CanecaResponse.class));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/tirantes")
    public ResponseEntity<TiranteResponse> pedirTirante(@Valid @RequestBody TiranteRequest tiranteRequest) throws NegocioException {
        tiranteRequest.setValor(null);
        var tirante = mapper.mapTo(tiranteRequest, Tirante.class);
        tirante.setPedido(pedidoInboundPort.buscarPeloId(tiranteRequest.getPedidoId()));
        return ResponseEntity.ok(mapper.mapTo(inboundPort.salvar(tirante), TiranteResponse.class));
    }

    @PutMapping("/inventario/{idProduto}")
    public ResponseEntity<ProdutoResponse> pedirProdutoDoInventario(@Valid @PathVariable Long idProduto,
                                                                    @RequestBody IdPedidoRequest idPedidoRequest) {
        Long idPedido = idPedidoRequest.getId();
        if(inboundPort.produtoExiste(idProduto) && pedidoInboundPort.pedidoExiste(idPedido)) {
            var produto = inboundPort.adicionarProdutoDoInventarioAoPedido(idProduto, idPedido);
            if (produto instanceof Camisa) {
                return ResponseEntity.ok(mapper.mapTo(produto, CamisaResponse.class));
            } else if (produto instanceof Caneca) {
                return ResponseEntity.ok(mapper.mapTo(produto, CanecaResponse.class));
            } else {
                return ResponseEntity.ok(mapper.mapTo(produto, TiranteResponse.class));
            }
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
