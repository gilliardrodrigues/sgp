package br.com.sgp.adapters.inbound;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sgp.adapters.inbound.mapper.CanecaMapper;
import br.com.sgp.adapters.inbound.request.CanecaRequest;
import br.com.sgp.adapters.inbound.response.CanecaResponse;
import br.com.sgp.application.ports.in.CanecaUseCaseInboundPort;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/canecas")
@AllArgsConstructor
public class CanecaController {

    private final CanecaUseCaseInboundPort inboundPort;
    private final CanecaMapper mapper;

    @PostMapping
    public CanecaResponse salvar(@RequestBody CanecaRequest request) {
        var caneca = mapper.requestToDomain(request);
        return mapper.domainToResponse(inboundPort.salvar(caneca.getValor(), caneca.getProntaEntrega(), caneca.getModelo()));
    }
}
