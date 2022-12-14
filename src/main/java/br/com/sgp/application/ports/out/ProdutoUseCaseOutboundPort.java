package br.com.sgp.application.ports.out;

import br.com.sgp.application.core.domain.*;
import br.com.sgp.application.core.exception.NegocioException;

import java.util.List;

public interface ProdutoUseCaseOutboundPort {

    Camisa salvarCamisa(Camisa camisa) throws NegocioException;
    Caneca salvarCaneca(Caneca caneca) throws NegocioException;
    Tirante salvarTirante(Tirante tirante) throws NegocioException;
    List<Camisa> buscarTodasCamisas();
    List<Caneca> buscarTodasCanecas();
    List<Tirante> buscarTodosTirantes();
    void excluir(Long id);
    Produto buscarPeloId(Long id) throws Throwable;
    List<Produto> buscarPeloIdPedido(Long idPedido) throws NegocioException;
    List<Camisa> buscarCamisasPeloIdPedido(Long idPedido);
    List<Caneca> buscarCanecasPeloIdPedido(Long idPedido);
    List<Tirante> buscarTirantesPeloIdPedido(Long idPedido);
    List<Camisa> buscarCamisasDoInventario();
    List<Caneca> buscarCanecasDoInventario();
    List<Tirante> buscarTirantesDoInventario();
    List<Tirante> buscarTirantePeloModelo(String modelo);
    List<Caneca> buscarCanecaPeloModelo(String modelo);
    List<Camisa> buscarCamisas(String cor, String tamanho, String curso);
    Boolean produtoExiste(Long id);
}
