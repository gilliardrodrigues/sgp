package br.com.sgp.application.ports.in;

import br.com.sgp.application.core.domain.Administrador;
import br.com.sgp.application.core.exception.AdministradorUseCaseException;

public interface AutenticarAdministradorUseCasePort {

    Administrador autenticar(String username, String senha) throws AdministradorUseCaseException;
}
