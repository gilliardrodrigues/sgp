package br.com.sgp.application.core.usecase;

import br.com.sgp.adapters.inbound.entity.FornecedorEntity;
import br.com.sgp.adapters.inbound.mapper.GenericMapper;
import br.com.sgp.application.core.domain.Observacao;
import br.com.sgp.application.core.exception.EntidadeNaoEncontradaException;
import br.com.sgp.application.ports.in.ObservacaoUseCaseInboundPort;
import br.com.sgp.application.ports.out.FornecedorUseCaseOutboundPort;
import br.com.sgp.application.ports.out.ObservacaoUseCaseOutboundPort;

import javax.transaction.Transactional;

public class ObservacaoUseCase implements ObservacaoUseCaseInboundPort {

    private final ObservacaoUseCaseOutboundPort outboundPort;
    private final FornecedorUseCaseOutboundPort fornecedorUseCaseOutboundPort;
    private final GenericMapper mapper;

    public ObservacaoUseCase(ObservacaoUseCaseOutboundPort outboundPort, FornecedorUseCaseOutboundPort fornecedorUseCaseOutboundPort) {

        this.outboundPort = outboundPort;
        this.fornecedorUseCaseOutboundPort = fornecedorUseCaseOutboundPort;
        mapper = new GenericMapper();
    }

    @Transactional
    @Override
    public Observacao cadastrar(Long idFornecedor, String comentario) {

        if(!fornecedorUseCaseOutboundPort.fornecedorExiste(idFornecedor)) {
            throw new EntidadeNaoEncontradaException("Fornecedor não encontrado!");
        }
        var fornecedor = fornecedorUseCaseOutboundPort.buscarPeloId(idFornecedor);
        var fornecedorEntity = mapper.mapTo(fornecedor, FornecedorEntity.class);
        var observacaoEntity = fornecedorEntity.cadastrarObservacao(comentario);
        return outboundPort.cadastrar(observacaoEntity);
    }

}
