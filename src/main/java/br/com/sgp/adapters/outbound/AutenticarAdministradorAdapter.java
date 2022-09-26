package br.com.sgp.adapters.outbound;

import br.com.sgp.adapters.inbound.mapper.GenericMapper;
import br.com.sgp.adapters.outbound.repository.AdministradorRepository;
import br.com.sgp.application.core.domain.Administrador;
import br.com.sgp.application.core.exception.EntidadeNaoEncontradaException;
import br.com.sgp.application.ports.out.AutenticarAdministradorPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AutenticarAdministradorAdapter implements AutenticarAdministradorPort {

    private final AdministradorRepository repository;
    private final GenericMapper mapper;


    @Override
    public Administrador autenticar(String username, String password) throws EntidadeNaoEncontradaException {

        var administradorEntity = repository.findByUsernameAndPassword(username, password);
        var administradorAutenticado = administradorEntity.
                orElseThrow(() -> new EntidadeNaoEncontradaException("Administrador não encontrado."));
        return mapper.mapTo(administradorAutenticado, Administrador.class);
    }
}
