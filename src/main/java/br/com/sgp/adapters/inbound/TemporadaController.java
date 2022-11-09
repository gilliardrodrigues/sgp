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

@CrossOrigin
@RestController
@RequestMapping("/temporadas")
@AllArgsConstructor
public class TemporadaController {

    private final TemporadaUseCaseInboundPort inboundPort;
    private final GenericMapper mapper;

    @GetMapping
    public List<FornecedorResponse> listar() {

        var temporadas = inboundPort.buscarTodas();
        return mapper.mapToList(temporadas, new TypeToken<List<TemporadaResponse>>() {
        }.getType());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemporadaResponse> buscarPeloId(@PathVariable Long id) {
        var temporada = inboundPort.buscarPeloId(id);
        return ResponseEntity.ok(mapper.mapTo(temporada, TemporadaResponse.class));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<TemporadaResponse> salvar(@Valid @RequestBody TemporadaRequest temporadaRequest)
            throws NegocioException {

        var temporada = mapper.mapTo(temporadaRequest, Temporada.class);
        return ResponseEntity.ok(mapper.mapTo(inboundPort.salvar(temporada), TemporadaResponse.class));
    }

    @PutMapping
    public ResponseEntity<TemporadaResponse> alterar(@RequestBody TemporadaRequest temporadaRequest)
            throws NegocioException {

        var temporada = mapper.mapTo(temporadaRequest, Temporada.class);
        // temporada.setId(id);
        return ResponseEntity.ok(mapper.mapTo(inboundPort.alterarTemporada(temporada), TemporadaResponse.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FornecedorResponse> excluir(@PathVariable Long id) {

        if (!inboundPort.temporadaExiste(id))
            return ResponseEntity.notFound().build();

        inboundPort.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/encerrar")
    public ResponseEntity<TemporadaResponse> encerrarTemporadaAtual() {
        if (inboundPort.existeTemporadaAtiva()) {
            return ResponseEntity.ok(mapper.mapTo(inboundPort.encerrarTemporadaAtual(), TemporadaResponse.class));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/ativa")
    public ResponseEntity<TemporadaResponse> buscarTemporadaAtiva() {
        return ResponseEntity.ok(mapper.mapTo(inboundPort.buscarAtiva(), TemporadaResponse.class));
    }
}
