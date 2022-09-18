package br.com.sgp.application.core.usecase;

import br.com.sgp.application.core.domain.Observacao;
import br.com.sgp.application.ports.in.ObservacaoUseCaseInboundPort;
import br.com.sgp.application.ports.out.ObservacaoUseCaseOutboundPort;

import javax.transaction.Transactional;

public class ObservacaoUseCase implements ObservacaoUseCaseInboundPort {

    private final ObservacaoUseCaseOutboundPort outboundPort;

    public ObservacaoUseCase(ObservacaoUseCaseOutboundPort outboundPort) {

        this.outboundPort = outboundPort;
    }

    @Transactional
    @Override
    public Observacao cadastrar(Long idFornecedor, String comentario) {

        return outboundPort.cadastrar(idFornecedor, comentario);
    }

}
