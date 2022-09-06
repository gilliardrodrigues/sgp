package br.com.sgp.application.core.usecase;

import br.com.sgp.application.core.domain.Administrador;
import br.com.sgp.application.core.exception.AdministradorUseCaseException;
import br.com.sgp.application.ports.in.AutenticarAdministradorUseCasePort;
import br.com.sgp.application.ports.out.AutenticarAdministradorPort;

public class AutenticarAdministradorUseCase implements AutenticarAdministradorUseCasePort {

    private final AutenticarAdministradorPort autenticarAdministradorPort;

    public AutenticarAdministradorUseCase(AutenticarAdministradorPort autenticarAdministradorPort) {
        this.autenticarAdministradorPort = autenticarAdministradorPort;
    }


    @Override
    public Administrador autenticar(String username, String password) throws AdministradorUseCaseException {
        return autenticarAdministradorPort.autenticar(username, password);
    }
}
