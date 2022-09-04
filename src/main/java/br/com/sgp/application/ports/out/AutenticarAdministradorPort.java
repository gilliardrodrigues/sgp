package br.com.sgp.application.ports.out;

import br.com.sgp.application.core.domain.Administrador;
import br.com.sgp.application.core.exception.AdministradorUseCaseException;

public interface AutenticarAdministradorPort {

    Administrador autenticar(String username, String password) throws AdministradorUseCaseException;
}
