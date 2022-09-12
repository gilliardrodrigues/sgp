package br.com.sgp.application.ports.out;

import br.com.sgp.application.core.domain.Fornecedor;
import br.com.sgp.application.core.exception.NegocioException;

import java.util.List;

public interface FornecedorUseCaseOutboundPort {

    Boolean fornecedorExiste(Long id);
    Fornecedor salvar(Fornecedor fornecedor) throws NegocioException;
    List<Fornecedor> buscarTodos();
    void excluir(Long id);
    Fornecedor buscarPeloId(Long id);
    List<Fornecedor> buscarPeloNome(String nome);
    List<Fornecedor> buscarPeloCNPJ(String CNPJ);
}
