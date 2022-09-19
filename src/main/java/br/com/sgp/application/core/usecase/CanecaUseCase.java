package br.com.sgp.application.core.usecase;

import br.com.sgp.application.core.domain.Caneca;
import br.com.sgp.application.ports.in.CanecaUseCaseInboundPort;
import br.com.sgp.application.ports.out.CanecaUseCaseOutboundPort;

public class CanecaUseCase implements CanecaUseCaseInboundPort{

    private final CanecaUseCaseOutboundPort outboundPort;

    public CanecaUseCase(CanecaUseCaseOutboundPort outboundPort) {

        this.outboundPort = outboundPort;
    }

    @Override
    public Caneca salvar(Double valor, Boolean prontaEntrega, String modelo) {

        return outboundPort.salvar(valor, prontaEntrega, modelo);
    }
}
