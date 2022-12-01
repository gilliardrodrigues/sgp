package br.com.sgp.application.core.usecase;

import br.com.sgp.adapters.inbound.entity.FornecedorEntity;
import br.com.sgp.adapters.inbound.mapper.GenericMapper;
import br.com.sgp.adapters.outbound.repository.FornecedorRepository;
import br.com.sgp.application.core.domain.Fornecedor;
import br.com.sgp.application.core.domain.TipoProduto;
import br.com.sgp.application.core.exception.EntidadeNaoEncontradaException;
import br.com.sgp.application.core.exception.NegocioException;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FornecedorUseCaseTest {

    @Mock
    private GenericMapper mapper;

    @Autowired
    private FornecedorUseCase usecase;

    @MockBean
    private FornecedorRepository repository;

    Fornecedor fornecedor;
    FornecedorEntity fornecedorEntity;

    final Long ID_BUSCADO = 1L;
    final String NOME_BUSCADO = "Fornecedor1";
    final String CNPJ_BUSCADO = "56.856.809/0001-74";

    @BeforeEach
    void setUp() {

        fornecedor = new Fornecedor(1L, "Fornecedor1", "56.856.809/0001-74",
                "fornecedor1@teste.com", 30, new ArrayList<>(),
                Lists.newArrayList(TipoProduto.CAMISA));
        fornecedorEntity = new FornecedorEntity(1L, "Fornecedor1", "56.856.809/0001-74",
                "fornecedor1@teste.com", 30, new ArrayList<>(),
                Lists.newArrayList(TipoProduto.CAMISA));
        given(mapper.mapTo(fornecedorEntity, Fornecedor.class)).willReturn(fornecedor);
    }

    @Test
    void testBuscarTodosOsFornecedores() {

        when(this.repository.findAll()).thenReturn(List.of(fornecedorEntity));

        final List<Fornecedor> fornecedores = this.usecase.buscarTodos();

        assertNotNull(fornecedores);
        assertFalse(fornecedores.isEmpty());
        verify(this.repository, times(1)).findAll();
    }

    @Test
    void testBuscarPeloId() throws EntidadeNaoEncontradaException {

        when(this.repository.findById(ID_BUSCADO)).thenReturn(Optional.of(fornecedorEntity));

        final Fornecedor fornecedorResponse = this.usecase.buscarPeloId(ID_BUSCADO);

        assertNotNull(fornecedorResponse);
        verify(this.repository, times(1)).findById(ID_BUSCADO);
    }

    @Test
    void testBuscarPeloIdQuandoNaoEncontra() throws EntidadeNaoEncontradaException {

        Exception exception = assertThrows(EntidadeNaoEncontradaException.class, () -> this.usecase.buscarPeloId(ID_BUSCADO));

        assertEquals("Fornecedor não encontrado!", exception.getMessage());
    }

    @Test
    void testBuscarPeloNome() {

        when(this.repository.findByRazaoSocialStartingWithIgnoreCase(NOME_BUSCADO)).thenReturn(List.of(fornecedorEntity));

        final List<Fornecedor> fornecedoresResponse = this.usecase.buscarPeloNome(NOME_BUSCADO);

        assertFalse(fornecedoresResponse.isEmpty());
        verify(this.repository, times(1)).findByRazaoSocialStartingWithIgnoreCase(NOME_BUSCADO);
    }

    @Test
    void testBuscarPeloNomeQuandoNaoEncontra() {

        when(this.repository.findByRazaoSocialStartingWithIgnoreCase(NOME_BUSCADO)).thenReturn(Collections.emptyList());

        final List<Fornecedor> fornecedoresResponse = this.usecase.buscarPeloNome(NOME_BUSCADO);

        assertTrue(fornecedoresResponse.isEmpty());
        verify(this.repository, times(1)).findByRazaoSocialStartingWithIgnoreCase(NOME_BUSCADO);
    }

    @Test
    void testBuscarPeloCNPJ() {

        when(this.repository.findByCNPJStartingWithIgnoreCase(CNPJ_BUSCADO)).thenReturn(List.of(fornecedorEntity));

        final List<Fornecedor> fornecedoresResponse = this.usecase.buscarPeloCNPJ(CNPJ_BUSCADO);

        assertFalse(fornecedoresResponse.isEmpty());
        verify(this.repository, times(1)).findByCNPJStartingWithIgnoreCase(anyString());
    }

    @Test
    void testBuscarPeloCNPJQuandoNaoEncontra() {

        when(this.repository.findByCNPJStartingWithIgnoreCase(CNPJ_BUSCADO)).thenReturn(Collections.emptyList());

        final List<Fornecedor> fornecedoresResponse = this.usecase.buscarPeloCNPJ(CNPJ_BUSCADO);

        assertTrue(fornecedoresResponse.isEmpty());
        verify(this.repository, times(1)).findByCNPJStartingWithIgnoreCase(CNPJ_BUSCADO);
    }

    @Test
    void testFornecedorExiste() {

        when(this.repository.existsById(ID_BUSCADO)).thenReturn(true);

        Boolean fornecedorExiste = this.usecase.fornecedorExiste(ID_BUSCADO);

        assertTrue(fornecedorExiste);
        verify(this.repository, times(1)).existsById(ID_BUSCADO);

    }

    @Test
    void testFornecedorNaoExiste() {

        when(this.repository.existsById(ID_BUSCADO)).thenReturn(false);

        Boolean fornecedorExiste = this.usecase.fornecedorExiste(ID_BUSCADO);

        assertFalse(fornecedorExiste);
        verify(this.repository, times(1)).existsById(ID_BUSCADO);

    }

    @Test
    void testSalvarFornecedor() {

        when(this.repository.save(any(FornecedorEntity.class))).thenReturn(fornecedorEntity);

        final Fornecedor fornecedorResponse = this.usecase.salvar(fornecedor);

        assertNotNull(fornecedorResponse);
        verify(this.repository, times(1)).save(any(FornecedorEntity.class));
    }

    @Test
    void testSalvarFornecedorQuandoJaExiste() {

        when(this.repository.existsByCNPJ(CNPJ_BUSCADO)).thenReturn(true);

        Exception exception = assertThrows(NegocioException.class, () -> this.usecase.salvar(fornecedor));

        assertEquals("Já existe um fornecedor cadastrado com esse CNPJ!", exception.getMessage());
    }

    @Test
    void testRemoverFornecedor() {

        doNothing().when(this.repository).deleteById(anyLong());

        this.usecase.excluir(1L);

        verify(this.repository, times(1)).deleteById(anyLong());
    }

}
