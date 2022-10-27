package br.com.sgp.application.core.usecase;

import br.com.sgp.application.core.domain.*;
import br.com.sgp.application.core.exception.NegocioException;
import br.com.sgp.application.ports.in.ProdutoUseCaseInboundPort;
import br.com.sgp.application.ports.out.ProdutoUseCaseOutboundPort;

import java.util.List;

public class ProdutoUseCase implements ProdutoUseCaseInboundPort {

    private final ProdutoUseCaseOutboundPort outboundPort;


    public ProdutoUseCase(ProdutoUseCaseOutboundPort outboundPort) {

        this.outboundPort = outboundPort;
    }

    public Boolean produtoExiste(Long id) {

        return outboundPort.produtoExiste(id);
    }

    @Override
    public Produto salvarInventario(Produto produto) throws NegocioException {

        produto.setProntaEntrega(true);
        if (produto.getTipo().equals(TipoProduto.CAMISA)) {
            Camisa camisa = (Camisa) produto;
            return outboundPort.salvarCamisa(camisa);
        } else if (produto.getTipo().equals(TipoProduto.CANECA)) {
            Caneca caneca = (Caneca) produto;
            return outboundPort.salvarCaneca(caneca);
        } else {
            Tirante tirante = (Tirante) produto;
            return outboundPort.salvarTirante(tirante);
        }
    }

    @Override
    public List<Produto> buscarTodos() {

        return outboundPort.buscarTodos();
    }

    @Override
    public List<Camisa> buscarTodasCamisas() {

        return outboundPort.buscarTodasCamisas();
    }

    @Override
    public List<Caneca> buscarTodasCanecas() {

        return outboundPort.buscarTodasCanecas();
    }

    @Override
    public List<Tirante> buscarTodosTirantes() {

        return outboundPort.buscarTodosTirantes();
    }

    @Override
    public void excluir(Long id) {

        outboundPort.excluir(id);
    }

    @Override
    public Produto buscarPeloId(Long id) throws Throwable {

        return outboundPort.buscarPeloId(id);
    }

    @Override
    public List<Produto> buscarPeloIdPedido(Long idPedido) throws NegocioException {

        return outboundPort.buscarPeloIdPedido(idPedido);
    }

    @Override
    public List<Produto> buscarInventario() {

        return outboundPort.buscarInventario();
    }

    @Override
    public List<Tirante> buscarTirantePeloModelo(String modelo) {

        return outboundPort.buscarTirantePeloModelo(modelo);
    }

    @Override
    public List<Caneca> buscarCanecaPeloModelo(String modelo) {

        return outboundPort.buscarCanecaPeloModelo(modelo);
    }

    @Override
    public List<Camisa> buscarCamisas(String cor, String tamanho, String curso) {

        return outboundPort.buscarCamisas(cor, tamanho, curso);
    }

}
