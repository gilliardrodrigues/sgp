package br.com.sgp.adapters.inbound;

import br.com.sgp.adapters.inbound.mapper.FornecedorMapper;
import br.com.sgp.adapters.inbound.request.FornecedorRequest;
import br.com.sgp.adapters.inbound.response.FornecedorResponse;
import br.com.sgp.application.core.domain.Fornecedor;
import br.com.sgp.application.core.exception.NegocioException;
import br.com.sgp.application.ports.in.FornecedorUseCaseInboundPort;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/fornecedores")
@AllArgsConstructor
public class FornecedorController {

    private final FornecedorUseCaseInboundPort inboundPort;
    private final FornecedorMapper mapper;

    @GetMapping
    public List<FornecedorResponse> listar() {

        var fornecedores = inboundPort.buscarTodos();
        return mapper.domainListToResponseList(fornecedores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponse> buscarPeloId(@PathVariable Long id) {

        var fornecedor = inboundPort.buscarPeloId(id);
        return ResponseEntity.ok(mapper.domainToResponse(fornecedor));
    }

    @GetMapping("/pesquisa/{texto}")
    public ResponseEntity<List<FornecedorResponse>> pesquisarPorNomeOuCnpj(@PathVariable(required = false) String texto) {

        List<Fornecedor> fornecedores;
        if (texto.matches("^[^A-Za-z]+$")) {
            fornecedores = inboundPort.buscarPeloCNPJ(texto);
        } else {
            fornecedores = inboundPort.buscarPeloNome(texto);
        }
        return fornecedores.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(mapper.domainListToResponseList(fornecedores));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void salvar(@Valid @RequestBody FornecedorRequest fornecedorRequest) throws NegocioException {

        var fornecedor = mapper.requestToDomain(fornecedorRequest);
        inboundPort.salvar(fornecedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorResponse> alterar(@Valid @PathVariable Long id, @RequestBody FornecedorRequest fornecedorRequest) throws NegocioException {

        var fornecedor = mapper.requestToDomain(fornecedorRequest);
        fornecedor.setId(id);
        return inboundPort.fornecedorExiste(id)
                ? ResponseEntity.ok(mapper.domainToResponse(inboundPort.salvar(fornecedor)))
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FornecedorResponse> excluir(@PathVariable Long id) {

        if (!inboundPort.fornecedorExiste(id))
            return ResponseEntity.notFound().build();

        inboundPort.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
