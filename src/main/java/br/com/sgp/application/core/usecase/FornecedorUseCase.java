package br.com.sgp.application.core.usecase;

import br.com.sgp.application.core.domain.Fornecedor;
import br.com.sgp.application.core.exception.NegocioException;
import br.com.sgp.application.ports.in.FornecedorUseCaseInboundPort;
import br.com.sgp.application.ports.out.FornecedorUseCaseOutboundPort;

import java.util.List;

public class FornecedorUseCase implements FornecedorUseCaseInboundPort {

    private final FornecedorUseCaseOutboundPort outboundPort;

    public FornecedorUseCase(FornecedorUseCaseOutboundPort outboundPort) {

        this.outboundPort = outboundPort;
    }

    @Override
    public Boolean fornecedorExiste(Long id) {

        return outboundPort.fornecedorExiste(id);
    }

    @Override
    public Fornecedor salvar(Fornecedor fornecedor) throws NegocioException {

        return outboundPort.salvar(fornecedor);
    }

    @Override
    public List<Fornecedor> buscarTodos() {

        return outboundPort.buscarTodos();
    }

    @Override
    public void excluir(Long id) {


        outboundPort.excluir(id);
    }

    @Override
    public Fornecedor buscarPeloId(Long id) {

        return outboundPort.buscarPeloId(id);
    }

    @Override
    public List<Fornecedor> buscarPeloNome(String nome) {

        return outboundPort.buscarPeloNome(nome);
    }

    @Override
    public List<Fornecedor> buscarPeloCNPJ(String CNPJ) {

        return outboundPort.buscarPeloCNPJ(CNPJ);
    }
}
