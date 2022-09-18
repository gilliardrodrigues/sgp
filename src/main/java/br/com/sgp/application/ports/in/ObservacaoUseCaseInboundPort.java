package br.com.sgp.application.ports.in;

import br.com.sgp.application.core.domain.Observacao;

public interface ObservacaoUseCaseInboundPort {

    Observacao cadastrar(Long idFornecedor, String comentario);
}
