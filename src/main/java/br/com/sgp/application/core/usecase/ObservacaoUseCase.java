package br.com.sgp.application.core.usecase;

import br.com.sgp.application.core.domain.Observacao;
import br.com.sgp.application.core.exception.EntidadeNaoEncontradaException;
import br.com.sgp.application.ports.in.ObservacaoUseCaseInboundPort;
import br.com.sgp.application.ports.out.FornecedorUseCaseOutboundPort;
import br.com.sgp.application.ports.out.ObservacaoUseCaseOutboundPort;

import javax.transaction.Transactional;
import java.util.List;

public class ObservacaoUseCase implements ObservacaoUseCaseInboundPort {

    private final ObservacaoUseCaseOutboundPort outboundPort;
    private final FornecedorUseCaseOutboundPort fornecedorUseCaseOutboundPort;

    public ObservacaoUseCase(ObservacaoUseCaseOutboundPort outboundPort, FornecedorUseCaseOutboundPort fornecedorUseCaseOutboundPort) {

        this.outboundPort = outboundPort;
        this.fornecedorUseCaseOutboundPort = fornecedorUseCaseOutboundPort;
    }

    @Transactional
    @Override
    public Observacao cadastrar(Long idFornecedor, String comentario) {

        if(!fornecedorUseCaseOutboundPort.fornecedorExiste(idFornecedor)) {
            throw new EntidadeNaoEncontradaException("Fornecedor não encontrado!");
        }
        var fornecedor = fornecedorUseCaseOutboundPort.buscarPeloId(idFornecedor);
        var observacao = fornecedor.cadastrarObservacao(comentario);

        return outboundPort.cadastrar(observacao);
    }

    @Override
    public List<Observacao> listarPorFornecedor(Long fornecedorId) {

        var fornecedor = fornecedorUseCaseOutboundPort.buscarPeloId(fornecedorId);
        return fornecedor.getObservacoes();
    }

}
