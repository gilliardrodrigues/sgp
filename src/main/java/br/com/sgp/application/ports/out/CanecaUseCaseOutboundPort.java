package br.com.sgp.application.ports.out;

import br.com.sgp.application.core.domain.Caneca;

public interface CanecaUseCaseOutboundPort {
    
    Caneca salvar(Double valor, Boolean prontaEntrega, String modelo);
}
