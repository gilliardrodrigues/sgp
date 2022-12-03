package br.com.sgp.application.core.usecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.sgp.adapters.inbound.entity.AlunoEmbeddable;
import br.com.sgp.adapters.inbound.entity.CamisaEntity;
import br.com.sgp.adapters.inbound.entity.CanecaEntity;
import br.com.sgp.adapters.inbound.entity.PedidoEntity;
import br.com.sgp.adapters.inbound.entity.ProdutoEntity;
import br.com.sgp.adapters.inbound.entity.TemporadaEntity;
import br.com.sgp.adapters.inbound.entity.TiranteEntity;
import br.com.sgp.adapters.inbound.mapper.GenericMapper;
import br.com.sgp.adapters.inbound.request.CanecaRequest;
import br.com.sgp.adapters.inbound.request.ProdutoRequest;
import br.com.sgp.adapters.outbound.ProdutoAdapter;
import br.com.sgp.adapters.outbound.TemporadaAdapter;
import br.com.sgp.adapters.outbound.repository.FornecedorRepository;
import br.com.sgp.adapters.outbound.repository.PedidoRepository;
import br.com.sgp.adapters.outbound.repository.ProdutoRepository;
import br.com.sgp.adapters.outbound.repository.TemporadaRepository;
import br.com.sgp.application.core.domain.Aluno;
import br.com.sgp.application.core.domain.Camisa;
import br.com.sgp.application.core.domain.Caneca;
import br.com.sgp.application.core.domain.CorCamisa;
import br.com.sgp.application.core.domain.Curso;
import br.com.sgp.application.core.domain.Observacao;
import br.com.sgp.application.core.domain.Pedido;
import br.com.sgp.application.core.domain.Produto;
import br.com.sgp.application.core.domain.StatusPagamento;
import br.com.sgp.application.core.domain.StatusPedido;
import br.com.sgp.application.core.domain.TamanhoCamisa;
import br.com.sgp.application.core.domain.Temporada;
import br.com.sgp.application.core.domain.TipoProduto;
import br.com.sgp.application.core.domain.Tirante;
import br.com.sgp.application.core.exception.NegocioException;
import br.com.sgp.application.ports.out.TemporadaUseCaseOutboundPort;
import org.json.JSONObject;


@SpringBootTest
public class ProdutoUseCaseTest {

    @Autowired
    private ProdutoUseCase usecase;

    private final Long ID_BUSCADO = 1L;

    @MockBean
    private PedidoRepository repository;

    @MockBean
    private TemporadaRepository temporadaRepository;

    @MockBean
    private ProdutoRepository produtoRepository;

    @MockBean
    private FornecedorRepository fornecedorRepository;

    @Mock
    private TemporadaUseCaseOutboundPort temporadaOutboundPort;

    @Mock
    private GenericMapper mapper;

    Temporada temporada;
    TemporadaEntity temporadaEntity;
    Pedido pedido;
    PedidoEntity pedidoEntity;

    @BeforeEach
    void setUp() {

        AlunoEmbeddable alunoEmbeddable = new AlunoEmbeddable("Joãozinho", "joaozinho@gmail.com", "(99) 9 9999-9999");
        Aluno aluno = new Aluno("Joãozinho", "joaozinho@gmail.com", "(99) 9 9999-9999");
        HashMap<TipoProduto, Integer> catalogo = new HashMap<>();
        catalogo.put(TipoProduto.CAMISA, 30);
        catalogo.put(TipoProduto.CANECA, 30);
        catalogo.put(TipoProduto.TIRANTE, 30);

        temporada = new Temporada(ID_BUSCADO, "Temporada 2022/2", OffsetDateTime.now(), null, catalogo,
                new ArrayList<>());
        temporadaEntity = new TemporadaEntity(ID_BUSCADO, "Temporada 2022/2", OffsetDateTime.now(), null, catalogo,
                new ArrayList<>());
        pedido = new Pedido(ID_BUSCADO, OffsetDateTime.now(), 0, StatusPedido.AGUARDANDO_PAGAMENTO,
                StatusPagamento.NAO_PAGO,
                0, temporada, aluno, null);
        pedidoEntity = new PedidoEntity(ID_BUSCADO, OffsetDateTime.now(), BigDecimal.ZERO,
                StatusPedido.AGUARDANDO_PAGAMENTO, StatusPagamento.NAO_PAGO,
                BigDecimal.ZERO, temporadaEntity, alunoEmbeddable, null);

        given(mapper.mapTo(temporadaEntity, Temporada.class)).willReturn(temporada);
        given(mapper.mapTo(temporada, TemporadaEntity.class)).willReturn(temporadaEntity);
        given(mapper.mapTo(pedidoEntity, Pedido.class)).willReturn(pedido);
        given(mapper.mapTo(pedido, PedidoEntity.class)).willReturn(pedidoEntity);

        when(this.temporadaRepository.findAllByDataFimIsNull()).thenReturn(List.of(temporadaEntity));
        when(this.temporadaRepository.findByDataFimIsNull()).thenReturn(Optional.of(temporadaEntity));
        when(this.repository.save(any(PedidoEntity.class))).thenReturn(pedidoEntity);
        when(this.repository.findById(pedidoEntity.getId())).thenReturn(Optional.of(pedidoEntity));

    }

    @Test
    void testSalvarCanecaNoInventario() throws JsonProcessingException {

        var caneca = new Caneca(1L, 100, null, null, null, null, null, null, "modelo");
        var canecaEntity = new CanecaEntity("modelo", 1L, 100, false, false, false, TipoProduto.CANECA, pedidoEntity,
                null);

        when(this.produtoRepository.save(any(ProdutoEntity.class))).thenReturn(canecaEntity);
        when(this.produtoRepository.findById(canecaEntity.getId())).thenReturn(Optional.of(canecaEntity));
        when(this.produtoRepository.existsById(canecaEntity.getId())).thenReturn(true);

        var canecaSalva = usecase.salvarInventario(caneca);
        assertNotNull(canecaSalva);
        assertTrue(usecase.produtoExiste(canecaSalva.getId()));

    }

    @Test
    void testSalvarTiranteNoInventario() throws JsonProcessingException {
        var tirante = new Tirante(1L, 100, null, null, null, null, null, null, "modelo");
        var tiranteEntity = new TiranteEntity("modelo", 1L, 100, null, null, null, null, null, null);

        when(this.produtoRepository.save(any(ProdutoEntity.class))).thenReturn(tiranteEntity);
        when(this.produtoRepository.findById(tiranteEntity.getId())).thenReturn(Optional.of(tiranteEntity));
        when(this.produtoRepository.existsById(tiranteEntity.getId())).thenReturn(true);

        var tiranteSalvo = usecase.salvarInventario(tirante);

        assertNotNull(tiranteSalvo);
        assertTrue(usecase.produtoExiste(tiranteSalvo.getId()));

    }

    @Test
    void testSalvarCamisaNoInventario() throws JsonProcessingException {
        var camisa = new Camisa(Curso.CIENCIA_DA_COMPUTACAO, TamanhoCamisa.G, CorCamisa.AZUL, null, null, null, null,
                null, null, null, null);
        var camisaEntity = new CamisaEntity(Curso.CIENCIA_DA_COMPUTACAO, TamanhoCamisa.G, CorCamisa.AZUL, null, null,
                null, null,
                null, null, null, null);

        when(this.produtoRepository.save(any(ProdutoEntity.class))).thenReturn(camisaEntity);
        when(this.produtoRepository.findById(camisaEntity.getId())).thenReturn(Optional.of(camisaEntity));
        when(this.produtoRepository.existsById(camisaEntity.getId())).thenReturn(true);

        var camisaSalva = usecase.salvarInventario(camisa);

        assertNotNull(camisaSalva);
        assertTrue(usecase.produtoExiste(camisaSalva.getId()));
        usecase.excluir(camisaSalva.getId());
    }

    @Test
    void testSalvarCaneca() throws JsonProcessingException {
        var caneca = new Caneca(1L, 100, false, false, false, TipoProduto.CANECA, pedido, null, "modelo");
        var canecaEntity = new CanecaEntity("modelo", 1L, 100, false, false, false, TipoProduto.CANECA, pedidoEntity,
                null);

        when(this.produtoRepository.save(any(ProdutoEntity.class))).thenReturn(canecaEntity);

        var canecaSalva = usecase.salvar(caneca);

        assertNotNull(canecaSalva);
    }

    @Test
    void testSalvarTirante() throws JsonProcessingException {
        var tirante = new Tirante(1L, 100, false, false, false, TipoProduto.TIRANTE, pedido, null, "modelo");
        var tiranteEntity = new TiranteEntity("modelo", 1L, 100, false, false, false, TipoProduto.TIRANTE, pedidoEntity,
                null);

        when(this.produtoRepository.save(any(ProdutoEntity.class))).thenReturn(tiranteEntity);

        var tiranteSalva = usecase.salvar(tirante);

        assertNotNull(tiranteSalva);
    }

    @Test
    void testSalvarCamisa() throws JsonProcessingException {
        var camisa = new Camisa(Curso.CIENCIA_DA_COMPUTACAO, TamanhoCamisa.G, CorCamisa.AZUL, null, null, false, false,
                false, TipoProduto.CAMISA, pedido, null);
        var camisaEntity = new CamisaEntity(Curso.CIENCIA_DA_COMPUTACAO, TamanhoCamisa.G, CorCamisa.AZUL, null, null,
                false, false,
                false, TipoProduto.CAMISA, pedidoEntity, null);

        when(this.produtoRepository.save(any(ProdutoEntity.class))).thenReturn(camisaEntity);

        var camisaSalva = usecase.salvar(camisa);

        assertNotNull(camisaSalva);
    }

    @Test
    void testBuscarProdutosVazios() throws JsonProcessingException {
        when(this.produtoRepository.findAllCamisas()).thenReturn(Collections.emptyList());
        when(this.produtoRepository.findAllCanecas()).thenReturn(Collections.emptyList());
        when(this.produtoRepository.findAllTirantes()).thenReturn(Collections.emptyList());

        assertEquals("[]",usecase.buscarTodos());
    }

    // @Test
    // void testBuscarInventario() throws JsonProcessingException {
    //     var canecaEntity = new CanecaEntity("modelo", 1L, 100, false, false, false,
    //             TipoProduto.CANECA, pedidoEntity,
    //             null);
    //     var tiranteEntity = new TiranteEntity("modelo", 1L, 100, null, null, null,
    //             null, null, null);
    //     var camisaEntity = new CamisaEntity(Curso.CIENCIA_DA_COMPUTACAO,
    //             TamanhoCamisa.G, CorCamisa.AZUL, null, null,
    //             null, null,
    //             null, null, null, null);

    //     when(this.produtoRepository.buscarCamisasDoInventario()).thenReturn(List.of(canecaEntity));
    //     when(this.produtoRepository.buscarCamisasDoInventario()).thenReturn(List.of(tiranteEntity));
    //     when(this.produtoRepository.buscarCamisasDoInventario()).thenReturn(List.of(camisaEntity));
    //     JSONObject vazio = null;
    //     when(.toMap()).thenReturn(null);

    //     assertNotNull(usecase.buscarInventario());
    // }

    // @Test
    // void testBuscarProdutosDePedido() throws JsonProcessingException {
    //     var canecaEntity = new CanecaEntity("modelo", 1L, 100, false, false, false,
    //             TipoProduto.CANECA, pedidoEntity,
    //             null);
    //     var tiranteEntity = new TiranteEntity("modelo", 1L, 100, null, null, null,
    //             null, null, null);
    //     var camisaEntity = new CamisaEntity(Curso.CIENCIA_DA_COMPUTACAO,
    //             TamanhoCamisa.G, CorCamisa.AZUL, null, null,
    //             null, null,
    //             null, null, null, null);

    //     when(this.produtoRepository.findAllCamisasByPedidoId(canecaEntity.getId())).thenReturn(List.of(canecaEntity));
    //     when(this.produtoRepository.findAllCamisasByPedidoId(tiranteEntity.getId())).thenReturn(List.of(tiranteEntity));
    //     when(this.produtoRepository.findAllCamisasByPedidoId(camisaEntity.getId())).thenReturn(List.of(camisaEntity));

    //     assertNotNull(usecase.buscarProdutosPeloIdPedido(pedidoEntity.getId()));
    // }

    // @Test
    // void testBuscarProdutos() throws JsonProcessingException {
    // var camisaEntity = new CamisaEntity(Curso.CIENCIA_DA_COMPUTACAO,
    // TamanhoCamisa.G, CorCamisa.AZUL, null, null,
    // null, null,
    // null, null, null, null);
    // var tiranteEntity = new TiranteEntity("modelo", 1L, 100, null, null, null,
    // null, null, null);
    // var canecaEntity = new CanecaEntity("modelo", 1L, 100, false, false, false,
    // TipoProduto.CANECA, pedidoEntity,
    // null);

    // when(this.produtoRepository.findAllCamisas()).thenReturn(List.of(camisaEntity));
    // when(this.produtoRepository.findAllCanecas()).thenReturn(List.of(tiranteEntity));
    // when(this.produtoRepository.findAllTirantes()).thenReturn(List.of(canecaEntity));
    // when(this.produtoRepository.findAllTirantes()).thenReturn(List.of(canecaEntity));

    // assertNotNull(usecase.buscarTodos());
    // }

    @Test
    void testAlterarCaneca() throws JsonProcessingException {
        var caneca = new Caneca(1L, 100, null, null, null, null, null, null, "modelo");
        var canecaEntity = new CanecaEntity("modelo2", 1L, 100, false, false, false, TipoProduto.CANECA, pedidoEntity,
                null);

        when(this.produtoRepository.save(any(ProdutoEntity.class))).thenReturn(canecaEntity);
        when(this.produtoRepository.findById(canecaEntity.getId())).thenReturn(Optional.of(canecaEntity));

        var canecaSalva = usecase.alterarCaneca(caneca);
        assertEquals("modelo2", canecaSalva.getModelo());
    }

    @Test
    void testAlterarCamisa() throws JsonProcessingException {
        var camisa = new Camisa(Curso.CIENCIA_DA_COMPUTACAO, TamanhoCamisa.G, CorCamisa.AZUL, 1l, null, null, null,
                null, null, null, null);
        var camisaEntity = new CamisaEntity(Curso.SISTEMAS_DE_INFORMACAO, TamanhoCamisa.G, CorCamisa.AZUL, 1l, null,
                null, null,
                null, null, null, null);

        when(this.produtoRepository.save(any(ProdutoEntity.class))).thenReturn(camisaEntity);
        when(this.produtoRepository.findById(camisaEntity.getId())).thenReturn(Optional.of(camisaEntity));

        var camisaSalva = usecase.alterarCamisa(camisa);
        assertEquals(Curso.SISTEMAS_DE_INFORMACAO, camisaSalva.getCurso());
    }

    @Test
    void testAlterarTirante() throws JsonProcessingException {
        var tirante = new Tirante(1L, 100, null, null, null, null, null, null, "modelo");
        var tiranteEntity = new TiranteEntity("modelo2", 1L, 100, null, null, null, null, null, null);

        when(this.produtoRepository.save(any(ProdutoEntity.class))).thenReturn(tiranteEntity);
        when(this.produtoRepository.findById(tiranteEntity.getId())).thenReturn(Optional.of(tiranteEntity));

        var tiranteSalva = usecase.alterarTirante(tirante);
        assertEquals("modelo2", tiranteSalva.getModelo());
    }

    @Test
    void testMarcarChegadaProduto() throws NegocioException {
        var canecaEntity = new CanecaEntity("modelo", 1L, 100, false, false, false,
                TipoProduto.CANECA, pedidoEntity,
                null);
        var tiranteEntity = new TiranteEntity("modelo", 1L, 100, null, null, null,
                null, null, null);
        var camisaEntity = new CamisaEntity(Curso.CIENCIA_DA_COMPUTACAO,
                TamanhoCamisa.G, CorCamisa.AZUL, null, null,
                null, null,
                null, null, null, null);

        when(this.produtoRepository.findAllCanecas()).thenReturn(List.of(canecaEntity));
        when(this.produtoRepository.findAllCamisas()).thenReturn(List.of(camisaEntity));
        when(this.produtoRepository.findAllTirantes()).thenReturn(List.of(canecaEntity));

        when(this.produtoRepository.save(any(CanecaEntity.class))).thenReturn(canecaEntity);
        when(this.produtoRepository.save(any(TiranteEntity.class))).thenReturn(tiranteEntity);
        when(this.produtoRepository.save(any(CamisaEntity.class))).thenReturn(camisaEntity);

        when(this.temporadaRepository.findAllByDataFimIsNull()).thenReturn(Collections.emptyList());

        assertDoesNotThrow(() -> usecase.marcarChegadaTipoDeProduto(TipoProduto.CANECA));
        assertDoesNotThrow(() -> usecase.marcarChegadaTipoDeProduto(TipoProduto.TIRANTE));
        assertDoesNotThrow(() -> usecase.marcarChegadaTipoDeProduto(TipoProduto.CAMISA));
    }

    @Test
    void testDesassociarProdutoDoPedido() throws NegocioException {
        var canecaEntity = new CanecaEntity("modelo", 1L, 100, false, false, false,
                TipoProduto.CANECA, pedidoEntity,
                null);

        when(this.repository.findById(pedidoEntity.getId())).thenReturn(Optional.of(pedidoEntity));
        when(this.produtoRepository.findById(canecaEntity.getId())).thenReturn(Optional.of(canecaEntity));

        when(this.produtoRepository.save(any(CanecaEntity.class))).thenReturn(canecaEntity);

        assertDoesNotThrow(() -> usecase.desassociarProdutoDoPedido(canecaEntity.getId(), pedidoEntity.getId()));
    }

    @Test
    void testAdicionarProdutoDoInventarioAoPedido() throws NegocioException {
        var canecaEntity = new CanecaEntity("modelo", 1L, 100, false, false, false,
                TipoProduto.CANECA, pedidoEntity,
                null);

        when(this.repository.findById(pedidoEntity.getId())).thenReturn(Optional.of(pedidoEntity));
        when(this.produtoRepository.findById(canecaEntity.getId())).thenReturn(Optional.of(canecaEntity));

        when(this.produtoRepository.save(any(CanecaEntity.class))).thenReturn(canecaEntity);

        assertDoesNotThrow(() -> usecase.adicionarProdutoDoInventarioAoPedido(canecaEntity.getId(), pedidoEntity.getId()));
    }

}
