package br.com.sgp.application.ports.in;

import br.com.sgp.application.core.domain.Caneca;

public interface CanecaUseCaseInboundPort {

    Caneca salvar(Double valor, Boolean prontaEntrega, String modelo);
}
