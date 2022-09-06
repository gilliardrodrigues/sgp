package br.com.sgp.adapters.inbound;

import br.com.sgp.adapters.inbound.mapper.AdministradorMapper;
import br.com.sgp.adapters.inbound.request.AdministradorRequest;
import br.com.sgp.adapters.inbound.response.AdministradorResponse;
import br.com.sgp.application.core.exception.AdministradorUseCaseException;
import br.com.sgp.application.ports.in.AutenticarAdministradorUseCasePort;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/administradores")
@AllArgsConstructor
public class AdministradorController {

    private final AutenticarAdministradorUseCasePort autenticarAdministradorUseCasePort;
    private final AdministradorMapper mapper;

    @PostMapping
    public AdministradorResponse autenticar(@RequestBody AdministradorRequest request) throws AdministradorUseCaseException {
        var administrador = mapper.requestToDomain(request);
        return mapper.domainToResponse(autenticarAdministradorUseCasePort.
                autenticar(administrador.getUsername(), administrador.getPassword()));
    }
}
