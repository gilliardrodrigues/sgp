package br.com.sgp.application.ports.in;

import br.com.sgp.application.core.domain.*;
import br.com.sgp.application.core.exception.NegocioException;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ProdutoUseCaseInboundPort {

    Produto salvarInventario(Produto produto) throws NegocioException;
    Produto salvar(Produto produto) throws NegocioException;
    String buscarTodos() throws JsonProcessingException;
    List<Camisa> buscarTodasCamisas();
    List<Caneca> buscarTodasCanecas();
    List<Tirante> buscarTodosTirantes();
    void excluir(Long id);
    Produto buscarPeloId(Long id) throws Throwable;
    List<Produto> buscarPeloIdPedido(Long idPedido) throws NegocioException;
    List<Produto> buscarInventario();
    List<Tirante> buscarTirantePeloModelo(String modelo);
    List<Caneca> buscarCanecaPeloModelo(String modelo);
    List<Camisa> buscarCamisas(String cor, String tamanho, String curso);
    Boolean produtoExiste(Long id);
}
