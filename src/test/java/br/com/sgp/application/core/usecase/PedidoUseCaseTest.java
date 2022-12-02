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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PedidoUseCaseTest {

    private final Long ID_BUSCADO = 1L;

    @Mock
    private GenericMapper mapper;

    @Autowired
    private PedidoUseCase usecase;

    @MockBean
    private PedidoRepository repository;

    @MockBean
    private TemporadaRepository temporadaRepository;

    @MockBean
    private ProdutoRepository produtoRepository;

    @MockBean
    private FornecedorRepository fornecedorRepository;

    Pedido pedido;
    PedidoEntity pedidoEntity;

    @Captor
    ArgumentCaptor<PedidoEntity> pedidoCaptor;

    Temporada temporada;
    TemporadaEntity temporadaEntity;

    @BeforeEach
    void setUp() {

        AlunoEmbeddable alunoEmbeddable = new AlunoEmbeddable("Joãozinho", "joaozinho@gmail.com", "(99) 9 9999-9999");
        Aluno aluno = new Aluno("Joãozinho", "joaozinho@gmail.com", "(99) 9 9999-9999");
        HashMap<TipoProduto, Integer> catalogo = new HashMap<>();
        catalogo.put(TipoProduto.CAMISA, 30);
        temporada = new Temporada(ID_BUSCADO, "Temporada 2022/2", OffsetDateTime.now(), null, catalogo, new ArrayList<>());
        temporadaEntity = new TemporadaEntity(ID_BUSCADO, "Temporada 2022/2", OffsetDateTime.now(), null, catalogo, new ArrayList<>());
        pedido = new Pedido(ID_BUSCADO, OffsetDateTime.now(), 0, StatusPedido.AGUARDANDO_PAGAMENTO, StatusPagamento.NAO_PAGO,
        0, temporada, aluno, null);
        pedidoEntity = new PedidoEntity(ID_BUSCADO, OffsetDateTime.now(), BigDecimal.ZERO, StatusPedido.AGUARDANDO_PAGAMENTO, StatusPagamento.NAO_PAGO,
                BigDecimal.ZERO, temporadaEntity, alunoEmbeddable, null);

        given(mapper.mapTo(temporadaEntity, Temporada.class)).willReturn(temporada);
        given(mapper.mapTo(temporada, TemporadaEntity.class)).willReturn(temporadaEntity);
        given(mapper.mapTo(pedidoEntity, Pedido.class)).willReturn(pedido);
        given(mapper.mapTo(pedido, PedidoEntity.class)).willReturn(pedidoEntity);

    }

    @Test
    void testSalvarPedido() {

        when(this.temporadaRepository.findAllByDataFimIsNull()).thenReturn(List.of(temporadaEntity));
        when(this.temporadaRepository.findByDataFimIsNull()).thenReturn(Optional.of(temporadaEntity));
        when(this.repository.save(any(PedidoEntity.class))).thenReturn(pedidoEntity);
        when(this.repository.findById(pedidoEntity.getId())).thenReturn(Optional.of(pedidoEntity));

        final Pedido pedidoResponse = this.usecase.salvar(pedido);

        assertNotNull(pedidoResponse);
        verify(this.temporadaRepository, times(1)).findAllByDataFimIsNull();
        verify(this.temporadaRepository, times(1)).findByDataFimIsNull();
        verify(this.repository, times(1)).save(any(PedidoEntity.class));
        verify(this.repository, times(1)).findById(anyLong());
    }

    @Test
    void testSalvarPedidoSemTemporadaAtiva() throws NegocioException {

        when(this.temporadaRepository.findAllByDataFimIsNull()).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(NegocioException.class, () -> this.usecase.salvar(pedido));

        assertEquals("Não foi possível criar o pedido, pois não há temporada ativa no momento!", exception.getMessage());
        verify(this.temporadaRepository, times(1)).findAllByDataFimIsNull();
    }

    @Test
    void testBuscarTodosPorTemporada() {

        when(this.repository.findAllByTemporadaId(1L)).thenReturn(List.of(pedidoEntity));

        List<Pedido> pedidosResponse = this.usecase.buscarTodosPorTemporada(1L);

        assertFalse(pedidosResponse.isEmpty());
        assertEquals(1L, pedidosResponse.get(0).getTemporada().getId());
        verify(this.repository, times(1)).findAllByTemporadaId(anyLong());
    }

    @Test
    void testRemoverPedido() {

        pedidoEntity.setValor(BigDecimal.valueOf(30));
        when(this.repository.findById(ID_BUSCADO)).thenReturn(Optional.of(pedidoEntity));
        CamisaEntity camisaEntity = new CamisaEntity(Curso.SISTEMAS_DE_INFORMACAO, TamanhoCamisa.G, CorCamisa.BRANCO,
                ID_BUSCADO, 30, false, false, false, TipoProduto.CAMISA, pedidoEntity, null);
        List<CamisaEntity> camisasEntity = List.of(camisaEntity);
        when(this.produtoRepository.findByPedidoId(ID_BUSCADO)).thenReturn(camisasEntity);
        when(this.repository.save(any(PedidoEntity.class))).thenReturn(pedidoEntity);
        doNothing().when(this.repository).deleteById(ID_BUSCADO);

        this.usecase.excluir(1L);

        verify(this.repository, times(1)).deleteById(1L);
    }

    @Test
    void testRemoverPedidoQueJaFoiPago() {

        pedidoEntity.setValor(BigDecimal.valueOf(30));
        pedidoEntity.setValorPago(BigDecimal.valueOf(30));
        pedidoEntity.setStatusPagamento(StatusPagamento.INTEGRALMENTE_PAGO);
        pedidoEntity.setSituacao(StatusPedido.CONFIRMADO);
        when(this.repository.findById(ID_BUSCADO)).thenReturn(Optional.of(pedidoEntity));
        CamisaEntity camisaEntity = new CamisaEntity(Curso.SISTEMAS_DE_INFORMACAO, TamanhoCamisa.G, CorCamisa.BRANCO,
                ID_BUSCADO, 30, false, false, false, TipoProduto.CAMISA, pedidoEntity, null);
        List<CamisaEntity> camisasEntity = List.of(camisaEntity);
        when(this.produtoRepository.findByPedidoId(1L)).thenReturn(camisasEntity);
        when(this.repository.save(any(PedidoEntity.class))).thenReturn(pedidoEntity);
        doNothing().when(this.repository).deleteById(ID_BUSCADO);

        Exception exception = assertThrows(NegocioException.class, () -> this.usecase.excluir(ID_BUSCADO));

        assertEquals("Não foi possível excluir o pedido, pois ele já foi pago!", exception.getMessage());
    }

    @Test
    void testRemoverPedidoQuandoNaoExiste() {

        Exception exception = assertThrows(EntidadeNaoEncontradaException.class, () -> this.usecase.excluir(ID_BUSCADO));

        assertEquals("Pedido não encontrado!", exception.getMessage());
    }

    @Test
    void testBuscarPeloId() {

        when(this.repository.findById(ID_BUSCADO)).thenReturn(Optional.of(pedidoEntity));
        CamisaEntity camisaEntity = new CamisaEntity(Curso.SISTEMAS_DE_INFORMACAO, TamanhoCamisa.G, CorCamisa.BRANCO,
                ID_BUSCADO, 30, false, false, false, TipoProduto.CAMISA, pedidoEntity, null);
        List<CamisaEntity> camisasEntity = List.of(camisaEntity);
        when(this.produtoRepository.findByPedidoId(ID_BUSCADO)).thenReturn(camisasEntity);
        when(this.repository.save(any(PedidoEntity.class))).thenReturn(pedidoEntity);

        Pedido pedidoResponse = this.usecase.buscarPeloId(ID_BUSCADO);

        assertEquals(ID_BUSCADO, pedidoResponse.getId());
        verify(this.repository, times(2)).findById(ID_BUSCADO);
    }

    @Test
    void testBuscarPeloIdQuandoNaoExiste() {

        Exception exception = assertThrows(EntidadeNaoEncontradaException.class, () -> this.usecase.buscarPeloId(ID_BUSCADO));

        assertEquals("Pedido não encontrado!", exception.getMessage());
    }

    @Test
    void testBuscarPedidosPassandoSituacaoAssimComoTemporadaId() {

        when(this.repository.findBySituacaoAndTemporadaId(StatusPedido.AGUARDANDO_PAGAMENTO, ID_BUSCADO)).thenReturn(List.of(pedidoEntity));

        pedidoEntity.setValor(BigDecimal.valueOf(30));
        pedidoEntity.setValorPago(BigDecimal.ZERO);
        pedidoEntity.setStatusPagamento(StatusPagamento.NAO_PAGO);
        pedidoEntity.setSituacao(StatusPedido.AGUARDANDO_PAGAMENTO);
        List<Pedido> pedidosResponse = this.usecase.buscarPedidos("AGUARDANDO_PAGAMENTO", null, null, null, null, ID_BUSCADO);

        assertTrue(pedidosResponse.get(0).getSituacao().equals(StatusPedido.AGUARDANDO_PAGAMENTO)
        && pedidosResponse.get(0).getTemporada().getId().equals(ID_BUSCADO));
        verify(this.repository, times(1)).findBySituacaoAndTemporadaId(StatusPedido.AGUARDANDO_PAGAMENTO, ID_BUSCADO);
    }

    @Test
    void testBuscarPedidosPassandoStatusPagamentoAssimComoTemporadaId() {

        pedidoEntity.setValor(BigDecimal.valueOf(30));
        pedidoEntity.setValorPago(BigDecimal.ZERO);
        pedidoEntity.setStatusPagamento(StatusPagamento.NAO_PAGO);
        pedidoEntity.setSituacao(StatusPedido.AGUARDANDO_PAGAMENTO);
        List<PedidoEntity> pedidosEntity = List.of(pedidoEntity);
        when(this.repository.findByStatusPagamentoAndTemporadaId(StatusPagamento.NAO_PAGO, ID_BUSCADO)).thenReturn(pedidosEntity);

        List<Pedido> pedidosResponse = this.usecase.buscarPedidos(null, "NAO_PAGO", null, null, null, ID_BUSCADO);

        assertTrue(pedidosResponse.get(0).getStatusPagamento().equals(StatusPagamento.NAO_PAGO)
                && pedidosResponse.get(0).getTemporada().getId().equals(ID_BUSCADO));
        verify(this.repository, times(1)).findByStatusPagamentoAndTemporadaId(StatusPagamento.NAO_PAGO, ID_BUSCADO);
    }

    @Test
    void testBuscarPedidosPassandoNomeAlunoAssimComoTemporadaId() {

        String nomeAluno = pedidoEntity.getAluno().getNome();
        List<PedidoEntity> pedidosEntity = List.of(pedidoEntity);
        when(this.repository.findByAlunoNomeAndTemporadaId(nomeAluno, ID_BUSCADO)).thenReturn(pedidosEntity);

        List<Pedido> pedidosResponse = this.usecase.buscarPedidos(null, null, nomeAluno, null, null, ID_BUSCADO);

        assertTrue(pedidosResponse.get(0).getAluno().getNome().equals(nomeAluno)
                && pedidosResponse.get(0).getTemporada().getId().equals(ID_BUSCADO));
        verify(this.repository, times(1)).findByAlunoNomeAndTemporadaId(nomeAluno, ID_BUSCADO);
    }

    @Test
    void testBuscarPedidosPassandoTipoProdutoAssimComoTemporadaId() {

        CamisaEntity camisaEntity = new CamisaEntity(Curso.SISTEMAS_DE_INFORMACAO, TamanhoCamisa.G, CorCamisa.BRANCO,
                ID_BUSCADO, 30, false, false, false, TipoProduto.CAMISA, pedidoEntity, null);
        List<CamisaEntity> camisasEntity = List.of(camisaEntity);
        when(produtoRepository.findAllCamisas()).thenReturn(camisasEntity);

        List<Pedido> pedidosResponse = this.usecase.buscarPedidos(null, null, null, null, "CAMISA", ID_BUSCADO);

        assertNotNull(pedidosResponse);
        verify(this.produtoRepository, times(1)).findAllCamisas();
    }

    @Test
    void testBuscarPedidosPassandoSomenteTemporadaId() {

        List<PedidoEntity> pedidosEntity = List.of(pedidoEntity);
        when(this.repository.findAllByTemporadaId(ID_BUSCADO)).thenReturn(pedidosEntity);

        List<Pedido> pedidosResponse = this.usecase.buscarPedidos(null, null, null, null, null, ID_BUSCADO);

        assertEquals(ID_BUSCADO, pedidosResponse.get(0).getTemporada().getId());
        verify(this.repository, times(1)).findAllByTemporadaId(ID_BUSCADO);
    }

    @Test
    void testBuscarPedidosPassandoDataAssimComoTemporadaId() {

        pedidoEntity.setData(OffsetDateTime.now());
        OffsetDateTime dataOffset = pedidoEntity.getData();
        Date data = Date.from(pedidoEntity.getData().toInstant());
        List<PedidoEntity> pedidosEntity = List.of(pedidoEntity);
        OffsetDateTime startDay = data.toInstant().atOffset(ZoneOffset.ofHours(-3)).plusHours(3);
        OffsetDateTime endDay = startDay
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999000).toInstant().atOffset(ZoneOffset.ofHours(-3));
        when(this.repository.findByDataBetweenAndTemporadaId(startDay, endDay, ID_BUSCADO)).thenReturn(pedidosEntity);

        List<Pedido> pedidosResponse = this.usecase.buscarPedidos(null, null, null, data, null, ID_BUSCADO);

        assertEquals(dataOffset, pedidosResponse.get(0).getData());
        assertEquals(ID_BUSCADO, pedidosResponse.get(0).getTemporada().getId());
        verify(this.repository, times(1)).findByDataBetweenAndTemporadaId(startDay, endDay, ID_BUSCADO);
    }

    @Test
    void testBuscarPedidosDaTemporadaPeloTipoDeProdutoPassandoCamisa() {

        CamisaEntity camisaEntity = new CamisaEntity(Curso.SISTEMAS_DE_INFORMACAO, TamanhoCamisa.G, CorCamisa.BRANCO,
                ID_BUSCADO, 30, false, false, false, TipoProduto.CAMISA, pedidoEntity, null);
        List<CamisaEntity> camisasEntity = List.of(camisaEntity);
        when(produtoRepository.findAllCamisas()).thenReturn(camisasEntity);

        Set<Pedido> pedidosResponse = this.usecase.buscarPedidosDaTemporadaPeloTipoDeProduto(ID_BUSCADO, TipoProduto.CAMISA);

        assertNotNull(pedidosResponse);
        verify(this.produtoRepository, times(1)).findAllCamisas();
    }

    @Test
    void testBuscarPedidosDaTemporadaPeloTipoDeProdutoPassandoCaneca() {

        CanecaEntity canecaEntity = new CanecaEntity("TELA AZUL", ID_BUSCADO, 30, false,
                false, false, TipoProduto.CANECA, pedidoEntity, null);
        List<CanecaEntity> canecasEntity = List.of(canecaEntity);
        when(produtoRepository.findAllCanecas()).thenReturn(canecasEntity);

        Set<Pedido> pedidosResponse = this.usecase.buscarPedidosDaTemporadaPeloTipoDeProduto(ID_BUSCADO, TipoProduto.CANECA);

        assertNotNull(pedidosResponse);
        verify(this.produtoRepository, times(1)).findAllCanecas();
    }

    @Test
    void testBuscarPedidosDaTemporadaPeloTipoDeProdutoPassandoTirante() {

        TiranteEntity tiranteEntity = new TiranteEntity("TELA AZUL", ID_BUSCADO, 30, false,
                false, false, TipoProduto.CANECA, pedidoEntity, null);
        List<TiranteEntity> tirantesEntity = List.of(tiranteEntity);
        when(produtoRepository.findAllTirantes()).thenReturn(tirantesEntity);

        Set<Pedido> pedidosResponse = this.usecase.buscarPedidosDaTemporadaPeloTipoDeProduto(ID_BUSCADO, TipoProduto.TIRANTE);

        assertNotNull(pedidosResponse);
        verify(this.produtoRepository, times(1)).findAllTirantes    ();
    }

    @Test
    void testDarBaixa() {

        pedidoEntity.setValor(BigDecimal.valueOf(30));
        when(repository.existsById(ID_BUSCADO)).thenReturn(true);
        when(repository.findById(ID_BUSCADO)).thenReturn(Optional.of(pedidoEntity));
        pedidoEntity.setValorPago(BigDecimal.valueOf(15));
        when(repository.save(any(PedidoEntity.class))).thenReturn(pedidoEntity);

        Pedido pedidoResponse = this.usecase.darBaixa(pedido.getId(), 15);

        assertNotNull(pedidoResponse);
        assertEquals(15, (int) pedidoResponse.getValorPago());

        verify(this.repository, times(1)).existsById(anyLong());
        verify(this.repository, times(1)).findById(anyLong());
        verify(this.repository, times(1)).save(any(PedidoEntity.class));
    }

    @Test
    void testDarBaixaComPagamentoMaior() {

        pedidoEntity.setValor(BigDecimal.valueOf(30));
        when(repository.existsById(ID_BUSCADO)).thenReturn(true);
        when(repository.findById(ID_BUSCADO)).thenReturn(Optional.of(pedidoEntity));

        Exception exception = assertThrows(NegocioException.class, () -> this.usecase.darBaixa(ID_BUSCADO, 50));

        assertEquals("Valor pago inválido", exception.getMessage());
    }

    @Test
    void testDarBaixaComPagamentoNegativo() {

        pedidoEntity.setValor(BigDecimal.valueOf(30));
        when(repository.existsById(ID_BUSCADO)).thenReturn(true);
        when(repository.findById(ID_BUSCADO)).thenReturn(Optional.of(pedidoEntity));

        Exception exception = assertThrows(NegocioException.class, () -> this.usecase.darBaixa(ID_BUSCADO, -10));

        assertEquals("Valor pago inválido", exception.getMessage());
    }

    @Test
    void testDarBaixaQuandoPedidoNaoExiste() {

        Exception exception = assertThrows(NegocioException.class, () -> this.usecase.darBaixa(ID_BUSCADO, 50));

        assertEquals("Pedido não encontrado", exception.getMessage());
    }

    @Test
    void testValidarStatusPedidoQuandoProdutoApenasChegou() {



        CamisaEntity camisaEntity = new CamisaEntity(Curso.SISTEMAS_DE_INFORMACAO, TamanhoCamisa.G, CorCamisa.BRANCO,
                ID_BUSCADO, 30, false, true, true, TipoProduto.CAMISA, pedidoEntity, null);
        List<CamisaEntity> camisasEntity = List.of(camisaEntity);
        pedidoEntity.setValor(BigDecimal.valueOf(30));
        pedidoEntity.setValorPago(BigDecimal.valueOf(30));
        pedidoEntity.setStatusPagamento(StatusPagamento.INTEGRALMENTE_PAGO);
        pedidoEntity.setSituacao(StatusPedido.CONFIRMADO);
        when(this.produtoRepository.findByPedidoId(ID_BUSCADO)).thenReturn(camisasEntity);
        when(this.repository.findById(ID_BUSCADO)).thenReturn(Optional.of(pedidoEntity));
        pedidoEntity.setSituacao(StatusPedido.PRONTO_PARA_ENTREGA);
        when(this.repository.save(pedidoCaptor.capture())).thenReturn(pedidoEntity);

        Pedido pedidoResponse = this.usecase.validarStatusPedido(ID_BUSCADO);
        PedidoEntity pedidoSalvo = pedidoCaptor.getValue();

        assertEquals(pedidoSalvo.getSituacao(), StatusPedido.PRONTO_PARA_ENTREGA);
        verify(this.produtoRepository, times(1)).findByPedidoId(ID_BUSCADO);
        verify(this.repository, times(1)).findById(ID_BUSCADO);
        verify(this.repository, times(1)).save(any(PedidoEntity.class));
    }

    /*
    @Test
    void testValidarStatusPedidoQuandoProdutoFoiEntregue() {

        //TODO
    }*/

    @Test
    void testBuscarPedidosConfirmadosPorTemporada() {

        pedidoEntity.setValor(BigDecimal.valueOf(30));
        pedidoEntity.setValorPago(BigDecimal.valueOf(30));
        pedidoEntity.setStatusPagamento(StatusPagamento.INTEGRALMENTE_PAGO);
        pedidoEntity.setSituacao(StatusPedido.CONFIRMADO);
        List<PedidoEntity> pedidosEntity = List.of(pedidoEntity);
        when(repository.findByTemporadaAndSituacao(any(TemporadaEntity.class), eq(StatusPedido.CONFIRMADO))).thenReturn(pedidosEntity);

        List<Pedido> pedidosResponse = this.usecase.buscarPedidosConfirmadosPorTemporada(temporada);

        assertNotNull(pedidosResponse);
        assertTrue(pedidosResponse.get(0).getTemporada().getId().equals(ID_BUSCADO) &&
                pedidosResponse.get(0).getSituacao().equals(StatusPedido.CONFIRMADO));
        verify(this.repository, times(1)).findByTemporadaAndSituacao(any(TemporadaEntity.class), eq(StatusPedido.CONFIRMADO));
    }

    @Test
    void testPedidoExiste() {

        when(this.repository.existsById(ID_BUSCADO)).thenReturn(true);

        Boolean pedidoExiste = this.usecase.pedidoExiste(ID_BUSCADO);

        assertTrue(pedidoExiste);
        verify(this.repository, times(1)).existsById(ID_BUSCADO);
    }

    /*
    @Test
    void EncerrarTemporadaDePedidos() {

        //TODO
    }*/
}
