package br.com.sgp.config;

import br.com.sgp.adapters.outbound.AutenticarAdministradorAdapter;
import br.com.sgp.application.core.usecase.AutenticarAdministradorUseCase;
import br.com.sgp.application.core.usecase.CanecaUseCase;
import br.com.sgp.application.core.usecase.FornecedorUseCase;
import br.com.sgp.application.core.usecase.ObservacaoUseCase;
import br.com.sgp.application.ports.out.CanecaUseCaseOutboundPort;
import br.com.sgp.application.ports.out.FornecedorUseCaseOutboundPort;
import br.com.sgp.application.ports.out.ObservacaoUseCaseOutboundPort;
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
    public ObservacaoUseCase observacaoUseCase(ObservacaoUseCaseOutboundPort outboundPort) {

        return new ObservacaoUseCase(outboundPort);
    }
    @Bean
    public CanecaUseCase canecaUseCase(CanecaUseCaseOutboundPort outboundPort) {

        return new CanecaUseCase(outboundPort);
    }
}
