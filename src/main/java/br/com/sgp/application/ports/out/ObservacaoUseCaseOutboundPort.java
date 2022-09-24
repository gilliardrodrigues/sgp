package br.com.sgp.application.ports.out;

import br.com.sgp.adapters.inbound.entity.ObservacaoEntity;
import br.com.sgp.application.core.domain.Observacao;

public interface ObservacaoUseCaseOutboundPort {

    Observacao cadastrar(ObservacaoEntity observacaoEntity);
}
