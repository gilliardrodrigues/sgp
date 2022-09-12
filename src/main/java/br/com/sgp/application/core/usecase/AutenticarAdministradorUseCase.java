package br.com.sgp.application.core.usecase;

import br.com.sgp.application.core.domain.Administrador;
import br.com.sgp.application.core.exception.EntidadeNaoEncontradaException;
import br.com.sgp.application.ports.in.AutenticarAdministradorUseCasePort;
import br.com.sgp.application.ports.out.AutenticarAdministradorPort;

public class AutenticarAdministradorUseCase implements AutenticarAdministradorUseCasePort {

    private final AutenticarAdministradorPort outboundPort;

    public AutenticarAdministradorUseCase(AutenticarAdministradorPort outboundPort) {

        this.outboundPort = outboundPort;
    }


    @Override
    public Administrador autenticar(String username, String password) throws EntidadeNaoEncontradaException {

        return outboundPort.autenticar(username, password);
    }
}
