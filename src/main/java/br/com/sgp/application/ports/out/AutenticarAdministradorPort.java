package br.com.sgp.application.ports.out;

import br.com.sgp.application.core.domain.Administrador;
import br.com.sgp.application.core.exception.EntidadeNaoEncontradaException;

public interface AutenticarAdministradorPort {

    Administrador autenticar(String username, String password) throws EntidadeNaoEncontradaException;
}
