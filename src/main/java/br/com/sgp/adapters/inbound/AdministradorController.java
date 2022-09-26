package br.com.sgp.adapters.inbound;

import br.com.sgp.adapters.inbound.mapper.GenericMapper;
import br.com.sgp.adapters.inbound.request.AdministradorRequest;
import br.com.sgp.adapters.inbound.response.AdministradorResponse;
import br.com.sgp.application.core.domain.Administrador;
import br.com.sgp.application.core.exception.EntidadeNaoEncontradaException;
import br.com.sgp.application.ports.in.AutenticarAdministradorUseCasePort;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/administradores")
@AllArgsConstructor
public class AdministradorController {

    private final AutenticarAdministradorUseCasePort inboundPort;
    private final GenericMapper mapper;

    @PostMapping
    public AdministradorResponse autenticar(@RequestBody AdministradorRequest request) throws EntidadeNaoEncontradaException {

        var administrador = mapper.mapTo(request, Administrador.class);
        return mapper.mapTo(inboundPort.autenticar(administrador.getUsername(), administrador.getPassword()), AdministradorResponse.class);
    }
}
