package br.com.sgp.adapters.inbound.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgp.adapters.inbound.entity.CanecaEntity;
import br.com.sgp.adapters.inbound.request.CanecaRequest;
import br.com.sgp.adapters.inbound.response.CanecaResponse;
import br.com.sgp.application.core.domain.Caneca;

@Component
public class CanecaMapper {
    
    @Autowired
    private ModelMapper modelMapper;

    public Caneca entityToDomain(CanecaEntity entity) {

        return modelMapper.map(entity, Caneca.class);
    }
    public Caneca requestToDomain(CanecaRequest request) {

        return modelMapper.map(request, Caneca.class);
    }
    public CanecaResponse domainToResponse(Caneca caneca) {

        return modelMapper.map(caneca, CanecaResponse.class);
    }
}
