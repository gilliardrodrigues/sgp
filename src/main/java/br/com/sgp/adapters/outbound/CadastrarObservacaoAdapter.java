package br.com.sgp.adapters.outbound;

import br.com.sgp.adapters.inbound.entity.ObservacaoEntity;
import br.com.sgp.adapters.inbound.mapper.GenericMapper;
import br.com.sgp.adapters.outbound.repository.ObservacaoRepository;
import br.com.sgp.application.core.domain.Observacao;
import br.com.sgp.application.ports.out.ObservacaoUseCaseOutboundPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class CadastrarObservacaoAdapter implements ObservacaoUseCaseOutboundPort {

    private final GenericMapper mapper;
    private final ObservacaoRepository repository;

    @Transactional
    @Override
    public Observacao cadastrar(Observacao observacao) {

        var observacaoEntity = mapper.mapTo(observacao, ObservacaoEntity.class);
        var observacaoSalva = repository.save(observacaoEntity);
        return mapper.mapTo(observacaoSalva, Observacao.class);
    }
}
