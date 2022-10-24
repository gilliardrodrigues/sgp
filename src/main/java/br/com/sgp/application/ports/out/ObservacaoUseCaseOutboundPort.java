package br.com.sgp.application.ports.out;

import br.com.sgp.application.core.domain.Observacao;

public interface ObservacaoUseCaseOutboundPort {

    Observacao cadastrar(Observacao observacao);
}
