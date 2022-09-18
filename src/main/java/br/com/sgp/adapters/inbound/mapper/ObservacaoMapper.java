package br.com.sgp.adapters.inbound.mapper;

import br.com.sgp.adapters.inbound.entity.ObservacaoEntity;
import br.com.sgp.adapters.inbound.response.ObservacaoResponse;
import br.com.sgp.application.core.domain.Observacao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ObservacaoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public List<ObservacaoResponse> domainListToResponseList(List<Observacao> observacoes) {

        return observacoes.stream()
                .map(this::domainToResponse)
                .collect(Collectors.toList());
    }
    public Observacao entityToDomain(ObservacaoEntity entity) {

        return modelMapper.map(entity, Observacao.class);
    }
    public ObservacaoResponse domainToResponse(Observacao observacao) {

        return modelMapper.map(observacao, ObservacaoResponse.class);
    }
}

