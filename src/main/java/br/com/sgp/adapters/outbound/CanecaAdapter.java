package br.com.sgp.adapters.outbound;

import br.com.sgp.adapters.inbound.entity.CanecaEntity;
import br.com.sgp.adapters.inbound.mapper.CanecaMapper;
import br.com.sgp.adapters.outbound.repository.CanecaRepository;
import br.com.sgp.application.core.domain.Caneca;
import br.com.sgp.application.ports.out.CanecaUseCaseOutboundPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CanecaAdapter implements CanecaUseCaseOutboundPort {

    private final CanecaRepository repository;
    private final CanecaMapper mapper;


    @Override
    public Caneca salvar(Double valor, Boolean prontaEntrega, String modelo) {

        var caneca = new CanecaEntity();
        caneca.setValor(valor);
        caneca.setProntaEntrega(prontaEntrega);
        caneca.setModelo(modelo);
        
        return mapper.entityToDomain(repository.save(caneca));
    }
}
