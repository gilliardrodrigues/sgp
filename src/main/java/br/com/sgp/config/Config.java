package br.com.sgp.config;

import br.com.sgp.adapters.outbound.AutenticarAdministradorAdapter;
import br.com.sgp.application.core.usecase.AutenticarAdministradorUseCase;
import br.com.sgp.application.core.usecase.FornecedorUseCase;
import br.com.sgp.application.ports.out.FornecedorUseCaseOutboundPort;
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
}
