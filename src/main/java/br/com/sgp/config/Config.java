package br.com.sgp.config;

import br.com.sgp.adapters.outbound.AutenticarAdministradorAdapter;
import br.com.sgp.application.core.usecase.*;
import br.com.sgp.application.ports.out.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public AutenticarAdministradorUseCase autenticarAdministradorUseCase(AutenticarAdministradorAdapter outboundPort) {

        return new AutenticarAdministradorUseCase(outboundPort);
    }
    @Bean
    public FornecedorUseCase fornecedorUseCase(FornecedorUseCaseOutboundPort outboundPort) {

        return new FornecedorUseCase(outboundPort);
    }
    @Bean
    public ObservacaoUseCase observacaoUseCase(ObservacaoUseCaseOutboundPort outboundPort, FornecedorUseCaseOutboundPort fornecedorUseCaseOutboundPort) {

        return new ObservacaoUseCase(outboundPort, fornecedorUseCaseOutboundPort);
    }
    @Bean
    public ProdutoUseCase produtoUseCase(ProdutoUseCaseOutboundPort outboundPort) {

        return new ProdutoUseCase(outboundPort);
    }
    @Bean
    public TemporadaUseCase temporadaUseCase(TemporadaUseCaseOutboundPort outboundPort) {

        return new TemporadaUseCase(outboundPort);
    }
    @Bean
    public PedidoUseCase pedidoUseCase(PedidoUseCaseOutboundPort outboundPort, ProdutoUseCaseOutboundPort produtoOutboundPort) {

        return new PedidoUseCase(outboundPort, produtoOutboundPort);
    }
}
