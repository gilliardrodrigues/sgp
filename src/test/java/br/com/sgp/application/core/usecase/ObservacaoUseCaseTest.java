package br.com.sgp.application.core.usecase;

import br.com.sgp.adapters.inbound.entity.FornecedorEntity;
import br.com.sgp.adapters.inbound.entity.ObservacaoEntity;
import br.com.sgp.adapters.inbound.mapper.GenericMapper;
import br.com.sgp.adapters.outbound.repository.FornecedorRepository;
import br.com.sgp.adapters.outbound.repository.ObservacaoRepository;
import br.com.sgp.application.core.domain.Fornecedor;
import br.com.sgp.application.core.domain.Observacao;
import br.com.sgp.application.core.domain.TipoProduto;
import br.com.sgp.application.core.exception.EntidadeNaoEncontradaException;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ObservacaoUseCaseTest {

    final Long ID_BUSCADO = 1L;
    final String COMENTARIO_TESTE = "Testando observacao!";

    @Mock
    private GenericMapper mapper;

    @Autowired
    private ObservacaoUseCase usecase;

    @MockBean
    private ObservacaoRepository repository;

    @MockBean
    private FornecedorRepository fornecedorRepository;

    Observacao observacao;
    ObservacaoEntity observacaoEntity;
    Fornecedor fornecedor;
    FornecedorEntity fornecedorEntity;

    @BeforeEach
    void setUp() {

        fornecedor = new Fornecedor(1L, "Fornecedor1", "56.856.809/0001-74",
                "fornecedor1@teste.com", 30, new ArrayList<>(),
                Lists.newArrayList(TipoProduto.CAMISA));
        fornecedorEntity = new FornecedorEntity(1L, "Fornecedor1", "56.856.809/0001-74",
                "fornecedor1@teste.com", 30, new ArrayList<>(),
                Lists.newArrayList(TipoProduto.CAMISA));
        observacao = new Observacao(1L, COMENTARIO_TESTE, OffsetDateTime.now(), fornecedor);
        observacaoEntity = new ObservacaoEntity(1L, COMENTARIO_TESTE, OffsetDateTime.now(), fornecedorEntity);

        given(mapper.mapTo(fornecedorEntity, Fornecedor.class)).willReturn(fornecedor);
        given(mapper.mapTo(observacaoEntity, Observacao.class)).willReturn(observacao);
    }

    @Test
    void testCadastrar() {

        when(this.fornecedorRepository.existsById(ID_BUSCADO)).thenReturn(true);
        when(this.fornecedorRepository.findById(ID_BUSCADO)).thenReturn(Optional.of(fornecedorEntity));
        when(this.repository.save(any(ObservacaoEntity.class))).thenReturn(observacaoEntity);

        final Observacao observacaoResponse = this.usecase.cadastrar(ID_BUSCADO, COMENTARIO_TESTE);

        assertNotNull(observacaoResponse);
        assertEquals(ID_BUSCADO, observacaoResponse.getFornecedor().getId());
        assertEquals(COMENTARIO_TESTE, observacaoResponse.getComentario());
        verify(this.repository, times(1)).save(any(ObservacaoEntity.class));
    }

    @Test
    void testCadastrarQuandoFornecedorNaoExiste() {

        Exception exception = assertThrows(EntidadeNaoEncontradaException.class, () -> this.usecase.cadastrar(ID_BUSCADO, COMENTARIO_TESTE));

        assertEquals("Fornecedor não encontrado!", exception.getMessage());
    }

    @Test
    void testListarPorFornecedor() {

        fornecedorEntity.setObservacoes(List.of(observacaoEntity));
        when(this.fornecedorRepository.findById(ID_BUSCADO)).thenReturn(Optional.of(fornecedorEntity));

        final List<Observacao> observacoesResponse = this.usecase.listarPorFornecedor(ID_BUSCADO);

        assertNotNull(observacoesResponse);
        assertEquals(observacaoEntity.getComentario(), observacoesResponse.get(0).getComentario());
        verify(this.fornecedorRepository, times(1)).findById(ID_BUSCADO);
    }

    @Test
    void testListarPorFornecedorQuandoNaoPossuiObservacao() {

        when(this.fornecedorRepository.findById(ID_BUSCADO)).thenReturn(Optional.of(fornecedorEntity));

        final List<Observacao> observacoesResponse = this.usecase.listarPorFornecedor(ID_BUSCADO);

        assertTrue(observacoesResponse.isEmpty());
        verify(this.fornecedorRepository, times(1)).findById(ID_BUSCADO);
    }
}
