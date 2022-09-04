package br.com.sgp.adapters.inbound.mapper;

import br.com.sgp.adapters.inbound.entity.AdministradorEntity;
import br.com.sgp.adapters.inbound.request.AdministradorRequest;
import br.com.sgp.adapters.inbound.response.AdministradorResponse;
import br.com.sgp.application.core.domain.Administrador;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdministradorMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Administrador entityToDomain(AdministradorEntity entity) {

        return modelMapper.map(entity, Administrador.class);
    }
    public Administrador requestToDomain(AdministradorRequest request) {
        return modelMapper.map(request, Administrador.class);
    }
    public AdministradorResponse domainToResponse(Administrador administrador) {
        return modelMapper.map(administrador, AdministradorResponse.class);
    }
}
