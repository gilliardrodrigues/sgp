package br.com.sgp.adapters.inbound;

import br.com.sgp.adapters.inbound.mapper.GenericMapper;
import br.com.sgp.adapters.inbound.request.TemporadaRequest;
import br.com.sgp.adapters.inbound.response.FornecedorResponse;
import br.com.sgp.adapters.inbound.response.TemporadaResponse;
import br.com.sgp.application.core.domain.Temporada;
import br.com.sgp.application.core.exception.NegocioException;
import br.com.sgp.application.ports.in.TemporadaUseCaseInboundPort;
import lombok.AllArgsConstructor;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/temporadas")
@AllArgsConstructor
public class TemporadaController {

    private final TemporadaUseCaseInboundPort inboundPort;
    private final GenericMapper mapper;

    @GetMapping
    public List<FornecedorResponse> listar() {

        var temporadas = inboundPort.buscarTodas();
        return mapper.mapToList(temporadas, new TypeToken<List<TemporadaResponse>>() {}.getType());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void salvar(@Valid @RequestBody TemporadaRequest temporadaRequest) throws NegocioException {

        var temporada = mapper.mapTo(temporadaRequest, Temporada.class);
        inboundPort.salvar(temporada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TemporadaResponse> alterar(@Valid @PathVariable Long id, @RequestBody TemporadaRequest temporadaRequest) throws NegocioException {

        var temporada = mapper.mapTo(temporadaRequest, Temporada.class);
        temporada.setId(id);
        return inboundPort.temporadaExiste(id)
                ? ResponseEntity.ok(mapper.mapTo(inboundPort.alterarTemporada(temporada), TemporadaResponse.class))
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FornecedorResponse> excluir(@PathVariable Long id) {

        if (!inboundPort.temporadaExiste(id))
            return ResponseEntity.notFound().build();

        inboundPort.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/encerrar/{id}")
    public ResponseEntity<TemporadaResponse> encerrarTemporada(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(mapper.mapTo(inboundPort.encerrarTemporada(id), TemporadaResponse.class));
         
    }

}
