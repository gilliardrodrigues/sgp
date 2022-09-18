package br.com.sgp.adapters.inbound;

import br.com.sgp.adapters.inbound.mapper.ObservacaoMapper;
import br.com.sgp.adapters.inbound.request.ObservacaoRequest;
import br.com.sgp.adapters.inbound.response.ObservacaoResponse;
import br.com.sgp.application.ports.in.FornecedorUseCaseInboundPort;
import br.com.sgp.application.ports.in.ObservacaoUseCaseInboundPort;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/fornecedores/{idFornecedor}/observacoes")
@AllArgsConstructor
public class ObservacaoController {

    private final FornecedorUseCaseInboundPort fornecedorUseCaseInboundPort;
    private final ObservacaoUseCaseInboundPort inboundPort;
    private final ObservacaoMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ObservacaoResponse cadastrar(@PathVariable Long idFornecedor, @Valid @RequestBody ObservacaoRequest request) {

        var observacao = inboundPort.cadastrar(idFornecedor, request.getComentario());
        return mapper.domainToResponse(observacao);
    }
    @GetMapping
    public ResponseEntity<List<ObservacaoResponse>> listar(@PathVariable Long idFornecedor) {

        if(fornecedorUseCaseInboundPort.fornecedorExiste(idFornecedor)) {
            var fornecedor = fornecedorUseCaseInboundPort.buscarPeloId(idFornecedor);
            return ResponseEntity.ok(mapper.domainListToResponseList(fornecedor.getObservacoes()));
        }
        return ResponseEntity.notFound().build();
    }
}
