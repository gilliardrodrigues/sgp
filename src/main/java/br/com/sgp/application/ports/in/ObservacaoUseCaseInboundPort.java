package br.com.sgp.application.ports.in;

import br.com.sgp.application.core.domain.Observacao;

import java.util.List;

public interface ObservacaoUseCaseInboundPort {

    Observacao cadastrar(Long idFornecedor, String comentario);
    List<Observacao> listarPorFornecedor(Long fornecedorId);
}
