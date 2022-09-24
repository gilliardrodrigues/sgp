package br.com.sgp.adapters.inbound;

import br.com.sgp.adapters.inbound.mapper.GenericMapper;
import br.com.sgp.adapters.inbound.request.CamisaRequest;
import br.com.sgp.adapters.inbound.request.CanecaRequest;
import br.com.sgp.adapters.inbound.request.TiranteRequest;
import br.com.sgp.adapters.inbound.response.CamisaResponse;
import br.com.sgp.adapters.inbound.response.CanecaResponse;
import br.com.sgp.adapters.inbound.response.ProdutoResponse;
import br.com.sgp.adapters.inbound.response.TiranteResponse;
import br.com.sgp.application.core.domain.*;
import br.com.sgp.application.core.exception.NegocioException;
import br.com.sgp.application.ports.in.ProdutoUseCaseInboundPort;
import lombok.AllArgsConstructor;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@AllArgsConstructor
public class ProdutoController {

    private final ProdutoUseCaseInboundPort inboundPort;
    private final GenericMapper mapper;

    @GetMapping
    public List<ProdutoResponse> listarProdutos() {

        var produtos = inboundPort.buscarTodos();
        return mapper.mapToList(produtos, new TypeToken<List<ProdutoResponse>>() {}.getType());
    }
    @GetMapping("/camisas")
    public List<CamisaResponse> listarCamisas() {

        var camisas = inboundPort.buscarTodasCamisas();
        return mapper.mapToList(camisas, new TypeToken<List<CamisaResponse>>() {}.getType());
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
    @GetMapping("/inventario")
    public List<ProdutoResponse> listarInventario() {

        var inventario = inboundPort.buscarInventario();
        return mapper.mapToList(inventario, new TypeToken<List<ProdutoResponse>>() {}.getType());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarPeloId(@PathVariable Long id) {

        try {
            var produto = inboundPort.buscarPeloId(id);
            return ResponseEntity.ok(mapper.mapTo(produto, ProdutoResponse.class));
        } catch (Throwable e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/tirantes/filtro/{modelo}")
    public ResponseEntity<List<TiranteResponse>> pesquisarTirantePorModelo(@PathVariable(required = false) String modelo) {

        List<Tirante> tirantes = inboundPort.buscarTirantePeloModelo(modelo);
        return ResponseEntity.ok(mapper.mapToList(tirantes, new TypeToken<List<TiranteResponse>>() {}.getType()));
    }
    @GetMapping("/canecas/filtro/{modelo}")
    public ResponseEntity<List<CanecaResponse>> pesquisarCanecaPorModelo(@PathVariable(required = false) String modelo) {

        List<Caneca> canecas = inboundPort.buscarCanecaPeloModelo(modelo);
        return ResponseEntity.ok(mapper.mapToList(canecas, new TypeToken<List<CanecaResponse>>() {}.getType()));
    }
    @GetMapping("/camisas/filtro/{cor}")
    public ResponseEntity<List<CamisaResponse>> pesquisarCamisaPelaCor(@PathVariable(required = false) CorCamisa cor) {

        List<Camisa> camisas = inboundPort.buscarPelaCor(cor);
        return ResponseEntity.ok(mapper.mapToList(camisas, new TypeToken<List<CamisaResponse>>() {}.getType()));
    }
    @GetMapping("/camisas/filtro/{tamanho}")
    public ResponseEntity<List<CamisaResponse>> pesquisarCamisaPeloTamanho(@PathVariable(required = false) TamanhoCamisa tamanho) {

        List<Camisa> camisas = inboundPort.buscarPeloTamanho(tamanho);
        return ResponseEntity.ok(mapper.mapToList(camisas, new TypeToken<List<CamisaResponse>>() {}.getType()));
    }
    @GetMapping("/camisas/filtro/{curso}")
    public ResponseEntity<List<CamisaResponse>> pesquisarCamisaPeloCurso(@PathVariable(required = false) Curso curso) {

        List<Camisa> camisas = inboundPort.buscarPeloCurso(curso);
        return ResponseEntity.ok(mapper.mapToList(camisas, new TypeToken<List<CamisaResponse>>() {}.getType()));
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/camisas")
    public void salvarCamisaNoInventario(@Valid @RequestBody CamisaRequest camisaRequest) throws NegocioException {

        var camisa = mapper.mapTo(camisaRequest, Camisa.class);
        inboundPort.salvar(camisa);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/canecas")
    public void salvarCanecaNoInventario(@Valid @RequestBody CanecaRequest canecaRequest) throws NegocioException {

        var caneca = mapper.mapTo(canecaRequest, Caneca.class);
        inboundPort.salvar(caneca);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/tirantes")
    public void salvarTiranteNoInventario(@Valid @RequestBody TiranteRequest tiranteRequest) throws NegocioException {

        var tirante = mapper.mapTo(tiranteRequest, Tirante.class);
        inboundPort.salvar(tirante);
    }
    @PutMapping("/admin/camisas/{id}")
    public ResponseEntity<CamisaResponse> alterarCamisa(@Valid @PathVariable Long id, @RequestBody CamisaRequest camisaRequest) throws NegocioException {

        var camisa = mapper.mapTo(camisaRequest, Camisa.class);
        camisa.setId(id);
        return inboundPort.produtoExiste(id)
                ? ResponseEntity.ok(mapper.mapTo(inboundPort.salvar(camisa), CamisaResponse.class))
                : ResponseEntity.notFound().build();
    }
    @PutMapping("/admin/canecas/{id}")
    public ResponseEntity<CanecaResponse> alterarCaneca(@Valid @PathVariable Long id, @RequestBody CanecaRequest canecaRequest) throws NegocioException {

        var caneca = mapper.mapTo(canecaRequest, Caneca.class);
        caneca.setId(id);
        return inboundPort.produtoExiste(id)
                ? ResponseEntity.ok(mapper.mapTo(inboundPort.salvar(caneca), CanecaResponse.class))
                : ResponseEntity.notFound().build();
    }
    @PutMapping("/admin/tirantes/{id}")
    public ResponseEntity<TiranteResponse> alterarTirante(@Valid @PathVariable Long id, @RequestBody TiranteRequest tiranteRequest) throws NegocioException {

        var tirante = mapper.mapTo(tiranteRequest, Tirante.class);
        tirante.setId(id);
        return inboundPort.produtoExiste(id)
                ? ResponseEntity.ok(mapper.mapTo(inboundPort.salvar(tirante), TiranteResponse.class))
                : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<ProdutoResponse> excluir(@PathVariable Long id) {

        if (!inboundPort.produtoExiste(id))
            return ResponseEntity.notFound().build();

        inboundPort.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
