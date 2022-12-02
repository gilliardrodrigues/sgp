package br.com.sgp.application.core.usecase;

import br.com.sgp.adapters.inbound.entity.*;
import br.com.sgp.adapters.inbound.mapper.GenericMapper;
import br.com.sgp.adapters.outbound.repository.FornecedorRepository;
import br.com.sgp.adapters.outbound.repository.PedidoRepository;
import br.com.sgp.adapters.outbound.repository.ProdutoRepository;
import br.com.sgp.adapters.outbound.repository.TemporadaRepository;
import br.com.sgp.application.core.domain.*;
import br.com.sgp.application.core.exception.EntidadeNaoEncontradaException;
import br.com.sgp.application.core.exception.NegocioException;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TemporadaUseCaseTest {

    private final Long ID_BUSCADO = 2L;

    @Mock
    private GenericMapper mapper;

    @Autowired
    private TemporadaUseCase usecase;

    @MockBean
    private TemporadaRepository repository;

    @MockBean
    private PedidoRepository pedidoRepository;

    @MockBean
    private FornecedorRepository fornecedorRepository;

    @MockBean
    private ProdutoRepository produtoRepository;

    private Temporada temporada;
    private TemporadaEntity temporadaEntity;

    @BeforeEach
    void setUp() {

        HashMap<TipoProduto, Integer> catalogo = new HashMap<>();
        catalogo.put(TipoProduto.CAMISA, 30);
        temporada = new Temporada(2L, "Temporada 2022/2", OffsetDateTime.now(), null, catalogo, new ArrayList<>());
        temporadaEntity = new TemporadaEntity(2L, "Temporada 2022/2", OffsetDateTime.now(), null, catalogo, new ArrayList<>());

        given(mapper.mapTo(temporadaEntity, Temporada.class)).willReturn(temporada);
    }

    @Test
    void testSalvarTemporada() {

        when(this.repository.save(any(TemporadaEntity.class))).thenReturn(temporadaEntity);

        final Temporada temporadaResponse = this.usecase.salvar(temporada);

        assertNotNull(temporadaResponse);
        verify(this.repository, times(1)).save(any(TemporadaEntity.class));
    }

    @Test
    void testSalvarTemporadaQuandoJaTemOutraAtiva() throws NegocioException {

        HashMap<TipoProduto, Integer> catalogo = new HashMap<>();
        catalogo.put(TipoProduto.CANECA, 20);
        catalogo.put(TipoProduto.TIRANTE, 10);
        TemporadaEntity temporadaEntity2 = new TemporadaEntity(1L, "Temporada 2022/2", OffsetDateTime.now().minusDays(50), null, catalogo, new ArrayList<>());
        when(this.repository.findAllByDataFimIsNull()).thenReturn(List.of(temporadaEntity2));

        Exception exception = assertThrows(NegocioException.class, () -> this.usecase.salvar(temporada));

        assertEquals("Não pode haver duas temporadas ativas ao mesmo tempo! Encerra a atual para abrir uma nova.", exception.getMessage());
    }

    @Test
    void testRemoverTemporada() {

        doNothing().when(this.repository).deleteById(1L);

        this.usecase.excluir(1L);

        verify(this.repository, times(1)).deleteById(1L);
    }

    @Test
    void testBuscarPeloId() {

        when(this.repository.findById(ID_BUSCADO)).thenReturn(Optional.of(temporadaEntity));

        final Temporada temporadaResponse = this.usecase.buscarPeloId(ID_BUSCADO);

        assertNotNull(temporadaResponse);
        verify(this.repository, times(1)).findById(ID_BUSCADO);
    }

    @Test
    void testBuscarPeloIdQuandoNaoEncontra() throws EntidadeNaoEncontradaException {

        Exception exception = assertThrows(EntidadeNaoEncontradaException.class, () -> this.usecase.buscarPeloId(ID_BUSCADO));

        assertEquals("Temporada não encontrada!", exception.getMessage());
    }

    @Test
    void testBuscarTodasAsTemporadas() {

        when(this.repository.findAll()).thenReturn(List.of(temporadaEntity));

        final List<Temporada> temporadas = this.usecase.buscarTodas();

        assertNotNull(temporadas);
        assertFalse(temporadas.isEmpty());
        verify(this.repository, times(1)).findAll();
    }

    @Test
    void testTemporadaExiste() {

        when(this.repository.existsById(ID_BUSCADO)).thenReturn(true);

        boolean temporadaExiste = this.usecase.temporadaExiste(ID_BUSCADO);

        assertTrue(temporadaExiste);
        verify(this.repository, times(1)).existsById(ID_BUSCADO);
    }

    @Test
    void testTemporadaNaoExiste() {

        when(this.repository.existsById(ID_BUSCADO)).thenReturn(false);

        boolean temporadaExiste = this.usecase.temporadaExiste(ID_BUSCADO);

        assertFalse(temporadaExiste);
        verify(this.repository, times(1)).existsById(ID_BUSCADO);

    }

    @Test
    void testBuscarAtiva(){

        when(this.repository.findByDataFimIsNull()).thenReturn(Optional.of(temporadaEntity));

        Temporada temporadaAtiva = this.usecase.buscarAtiva();

        assertNotNull(temporadaAtiva);
        verify(this.repository, times(1)).findByDataFimIsNull();
    }

    @Test
    void testBuscarAtivaQuandoNaoTem() throws EntidadeNaoEncontradaException {

        Exception exception = assertThrows(EntidadeNaoEncontradaException.class, () -> this.usecase.buscarAtiva());

        assertEquals("Não existe temporada ativa!", exception.getMessage());
    }

    @Test
    void testExisteTemporadaAtiva() {

        when(this.repository.findAllByDataFimIsNull()).thenReturn(List.of(temporadaEntity));

        boolean existeTemporadaAtiva = this.usecase.existeTemporadaAtiva();

        assertTrue(existeTemporadaAtiva);
        verify(this.repository, times(1)).findAllByDataFimIsNull();
    }

    @Test
    void testAdicionarProdutos() {

        HashMap<TipoProduto, Integer> produtos = new HashMap<>();
        produtos.put(TipoProduto.CANECA, 20);
        produtos.put(TipoProduto.TIRANTE, 10);
        Temporada temporadaResponse = this.usecase.adicionarProdutos(temporada, produtos);

        assertEquals(3, temporadaResponse.getCatalogo().size());
    }

    @Test
    void testAlterarTemporada() {

        HashMap<TipoProduto, Integer> novoCatalogo = new HashMap<>();
        novoCatalogo.put(TipoProduto.CAMISA, 30);
        novoCatalogo.put(TipoProduto.CANECA, 20);
        novoCatalogo.put(TipoProduto.TIRANTE, 10);
        Temporada temporadaAlterada = new Temporada(1L, "Temporada 2022/2 DACompSI",
                OffsetDateTime.now(), null, novoCatalogo, new ArrayList<>());
        TemporadaEntity temporadaEntityAlterada = new TemporadaEntity(1L, "Temporada 2022/2 DACompSI",
                OffsetDateTime.now(), null, novoCatalogo, new ArrayList<>());

        when(this.repository.findByDataFimIsNull()).thenReturn(Optional.of(temporadaEntity));
        when(this.repository.save(any(TemporadaEntity.class))).thenReturn(temporadaEntityAlterada);

        Temporada temporadaResponse = this.usecase.alterarTemporada(temporadaAlterada);

        assertNotNull(temporadaResponse);
        assertEquals(3, temporadaResponse.getCatalogo().size());
        verify(this.repository, times(1)).findByDataFimIsNull();
        verify(this.repository, times(1)).save(any(TemporadaEntity.class));
    }

    @Test
    void testEncerrarTemporadaAtual() {

        AlunoEmbeddable aluno = new AlunoEmbeddable("Joãozinho", "joaozinho@gmail.com", "(99) 9 9999-9999");
        PedidoEntity pedidoEntity = new PedidoEntity(1L, OffsetDateTime.now().minusDays(30), BigDecimal.valueOf(50),
                StatusPedido.AGUARDANDO_PAGAMENTO, StatusPagamento.NAO_PAGO, BigDecimal.ZERO, temporadaEntity, aluno,
                OffsetDateTime.now().plusDays(30));
        List<PedidoEntity> pedidosNaoConfirmadosEntity = List.of(pedidoEntity);

        AlunoEmbeddable aluno2 = new AlunoEmbeddable("Guilherme", "guilherme@gmail.com", "(99) 9 9999-9999");
        PedidoEntity pedido2Entity = new PedidoEntity(2L, OffsetDateTime.now().minusDays(29), BigDecimal.valueOf(30),
                StatusPedido.CONFIRMADO, StatusPagamento.INTEGRALMENTE_PAGO, BigDecimal.valueOf(30), temporadaEntity, aluno2,
                OffsetDateTime.now().plusDays(30));
        List<PedidoEntity> pedidosConfirmadosEntity = List.of(pedidoEntity);



        when(this.repository.findByDataFimIsNull()).thenReturn(Optional.of(temporadaEntity));
        when(this.pedidoRepository.findByTemporadaAndSituacao(temporadaEntity, StatusPedido.AGUARDANDO_PAGAMENTO))
                .thenReturn(pedidosNaoConfirmadosEntity);
        when(this.pedidoRepository.findByTemporadaAndSituacao(temporadaEntity, StatusPedido.CONFIRMADO))
                .thenReturn(pedidosConfirmadosEntity);
        doNothing().when(this.pedidoRepository).deleteById(1L);
        FornecedorEntity fornecedorEntity = new FornecedorEntity(1L, "Fornecedor1", "56.856.809/0001-74",
                "fornecedor1@teste.com", 30, new ArrayList<>(), Lists.newArrayList(TipoProduto.CAMISA));
        List<FornecedorEntity> fornecedoresEntity = List.of(fornecedorEntity);
        CamisaEntity camisaEntity = new CamisaEntity(Curso.SISTEMAS_DE_INFORMACAO, TamanhoCamisa.G, CorCamisa.BRANCO,
                1L, 30, false, false, false, TipoProduto.CAMISA, pedido2Entity, null);
        List<CamisaEntity> camisasEntity = List.of(camisaEntity);
        when(this.fornecedorRepository.findAll()).thenReturn(fornecedoresEntity);
        when(this.produtoRepository.findAllCamisasByPedidoId(2L)).thenReturn(camisasEntity);
        when(this.produtoRepository.save(any(CamisaEntity.class))).thenReturn(camisaEntity);
        when(this.pedidoRepository.save(any(PedidoEntity.class))).thenReturn(pedido2Entity);
        temporadaEntity.setDataFim(OffsetDateTime.now());
        when(this.repository.save(any(TemporadaEntity.class))).thenReturn(temporadaEntity);

        Temporada temporadaResponse = this.usecase.encerrarTemporadaAtual();

        assertNotNull(temporadaResponse.getDataFim());

        verify(this.repository, times(1)).findByDataFimIsNull();
        verify(this.pedidoRepository, times(2)).findByTemporadaAndSituacao(any(TemporadaEntity.class), any());
        verify(this.fornecedorRepository, times(1)).findAll();
        verify(this.repository, times(1)).save(any(TemporadaEntity.class));
    }
}
