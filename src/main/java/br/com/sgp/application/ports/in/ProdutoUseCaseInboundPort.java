package br.com.sgp.application.ports.in;

import br.com.sgp.application.core.domain.*;
import br.com.sgp.application.core.exception.NegocioException;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ProdutoUseCaseInboundPort {

    Produto salvarInventario(Produto produto) throws NegocioException;
    Produto salvar(Produto produto) throws NegocioException;

    Camisa alterarCamisa(Camisa camisaRequest);

    Caneca alterarCaneca(Caneca canecaRequest);

    Tirante alterarTirante(Tirante tiranteRequest);

    void marcarChegadaTipoDeProduto(TipoProduto tipoProduto);

    String buscarTodos() throws JsonProcessingException;
    List<Camisa> buscarTodasCamisas();
    List<Caneca> buscarTodasCanecas();
    List<Tirante> buscarTodosTirantes();
    void excluir(Long id);
    Produto buscarPeloId(Long id) throws Throwable;
    String buscarProdutosPeloIdPedido(Long idPedido) throws JsonProcessingException;
    String buscarInventario() throws JsonProcessingException;
    List<Tirante> buscarTirantePeloModelo(String modelo);
    List<Caneca> buscarCanecaPeloModelo(String modelo);
    List<Camisa> buscarCamisas(String cor, String tamanho, String curso);
    Boolean produtoExiste(Long id);
    Produto adicionarProdutoDoInventarioAoPedido(Long idProduto, Long idPedido);

    Produto desassociarProdutoDoPedido(Long idProduto, Long idPedido);
}
