package br.com.sgp.application.core.usecase;

import br.com.sgp.adapters.inbound.entity.AdministradorEntity;
import br.com.sgp.adapters.inbound.mapper.GenericMapper;
import br.com.sgp.adapters.outbound.repository.AdministradorRepository;
import br.com.sgp.application.core.domain.Administrador;
import br.com.sgp.application.core.exception.EntidadeNaoEncontradaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AutenticarAdministradorUseCaseTest {
    @Mock
    private GenericMapper mapper;

    @MockBean
    private AdministradorRepository repository;

    @Autowired
    private AutenticarAdministradorUseCase usecase;

    private String username;
    private String password;

    @BeforeEach
    void setUp() {
        username = "admin";
        password = "12345678";
    }

    @Test
    void testAutenticar() throws EntidadeNaoEncontradaException {

        Administrador administrador = new Administrador(1L, username, password, "AdminTeste", "teste@sgp.com");
        AdministradorEntity administradorEntity = new AdministradorEntity(1L, username, password, "AdminTeste", "teste@sgp.com");

        given(mapper.mapTo(administradorEntity, Administrador.class)).willReturn(administrador);
        when(this.repository.findByUsernameAndPassword(username, password)).thenReturn(Optional.of(administradorEntity));

        final Administrador administradorResponse = this.usecase.autenticar(username, password);

        assertNotNull(administradorResponse);
        verify(this.repository, times(1)).findByUsernameAndPassword(anyString(), anyString());
    }

    @Test
    void testAutenticarNaoEncontraAdministrador() throws EntidadeNaoEncontradaException {
        Exception exception = assertThrows(EntidadeNaoEncontradaException.class, () -> this.usecase.autenticar(anyString(), anyString()));

        assertEquals("Administrador não encontrado.", exception.getMessage());
    }

}
