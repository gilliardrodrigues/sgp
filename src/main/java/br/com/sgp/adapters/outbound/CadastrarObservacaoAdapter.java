package br.com.sgp.adapters.outbound;

import br.com.sgp.adapters.inbound.mapper.FornecedorMapper;
import br.com.sgp.adapters.inbound.mapper.ObservacaoMapper;
import br.com.sgp.adapters.outbound.repository.ObservacaoRepository;
import br.com.sgp.application.core.domain.Observacao;
import br.com.sgp.application.core.exception.EntidadeNaoEncontradaException;
import br.com.sgp.application.ports.out.FornecedorUseCaseOutboundPort;
import br.com.sgp.application.ports.out.ObservacaoUseCaseOutboundPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class CadastrarObservacaoAdapter implements ObservacaoUseCaseOutboundPort {

    private final FornecedorUseCaseOutboundPort fornecedorUseCaseOutboundPort;
    private final FornecedorMapper fornecedorMapper;
    private final ObservacaoMapper mapper;

    private final ObservacaoRepository repository;

    @Transactional
    @Override
    public Observacao cadastrar(Long idFornecedor, String comentario) {

        if(!fornecedorUseCaseOutboundPort.fornecedorExiste(idFornecedor)) {
            throw new EntidadeNaoEncontradaException("Fornecedor não encontrado!");
        }
        var fornecedor = fornecedorUseCaseOutboundPort.buscarPeloId(idFornecedor);
        var fornecedorEntity = fornecedorMapper.domainToEntity(fornecedor);
        var observacao = fornecedorEntity.cadastrarObservacao(comentario);

        return mapper.entityToDomain(repository.save(observacao));
    }
}
