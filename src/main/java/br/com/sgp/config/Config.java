package br.com.sgp.config;

import br.com.sgp.adapters.outbound.AutenticarAdministradorAdapter;
import br.com.sgp.application.core.usecase.AutenticarAdministradorUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public AutenticarAdministradorUseCase autenticarAdministradorUseCase(AutenticarAdministradorAdapter autenticarAdministradorAdapter) {
        return new AutenticarAdministradorUseCase(autenticarAdministradorAdapter);
    }
}
