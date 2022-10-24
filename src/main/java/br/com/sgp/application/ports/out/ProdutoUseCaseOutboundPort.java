package br.com.sgp.application.ports.out;

import br.com.sgp.application.core.domain.Camisa;
import br.com.sgp.application.core.domain.Caneca;
import br.com.sgp.application.core.domain.Produto;
import br.com.sgp.application.core.domain.Tirante;
import br.com.sgp.application.core.exception.NegocioException;

import java.util.List;

public interface ProdutoUseCaseOutboundPort {

    Produto salvar(Produto produto) throws NegocioException;

    List<Produto> buscarTodos();

    List<Camisa> buscarTodasCamisas();

    List<Caneca> buscarTodasCanecas();

    List<Tirante> buscarTodosTirantes();

    void excluir(Long id);

    Produto buscarPeloId(Long id) throws Throwable;

    // List<Produto> buscarPeloIdPedido(Long idPedido) throws NegocioException;
    List<Produto> buscarInventario();

    List<Tirante> buscarTirantePeloModelo(String modelo);

    List<Caneca> buscarCanecaPeloModelo(String modelo);

    List<Camisa> buscarCamisas(String cor, String tamanho, String curso);

    Boolean produtoExiste(Long id);
}
