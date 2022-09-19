package br.com.sgp.adapters.inbound.mapper;

import br.com.sgp.adapters.inbound.entity.FornecedorEntity;
import br.com.sgp.adapters.inbound.request.FornecedorRequest;
import br.com.sgp.adapters.inbound.response.FornecedorResponse;
import br.com.sgp.application.core.domain.Fornecedor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FornecedorMapper {

    @Autowired
    private ModelMapper modelMapper;

    public List<Fornecedor> entityListToDomainList(List<FornecedorEntity> fornecedores) {

        return fornecedores.stream()
                .map(this::entityToDomain)
                .collect(Collectors.toList());
    }

    public List<FornecedorResponse> domainListToResponseList(List<Fornecedor> fornecedores) {

        return fornecedores.stream()
                .map(this::domainToResponse)
                .collect(Collectors.toList());
    }
    public Fornecedor entityToDomain(FornecedorEntity entity) {

        return modelMapper.map(entity, Fornecedor.class);
    }
    public FornecedorEntity domainToEntity(Fornecedor fornecedor) {

        return modelMapper.map(fornecedor, FornecedorEntity.class);
    }

    public Fornecedor requestToDomain(FornecedorRequest request) {

        return modelMapper.map(request, Fornecedor.class);
    }
    public FornecedorResponse domainToResponse(Fornecedor fornecedor) {

        return modelMapper.map(fornecedor, FornecedorResponse.class);
    }
}
