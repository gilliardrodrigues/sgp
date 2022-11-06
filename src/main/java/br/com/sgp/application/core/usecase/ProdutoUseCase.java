package br.com.sgp.application.core.usecase;

import br.com.sgp.application.core.domain.*;
import br.com.sgp.application.core.exception.NegocioException;
import br.com.sgp.application.ports.in.ProdutoUseCaseInboundPort;
import br.com.sgp.application.ports.out.PedidoUseCaseOutboundPort;
import br.com.sgp.application.ports.out.ProdutoUseCaseOutboundPort;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ProdutoUseCase implements ProdutoUseCaseInboundPort {

    private final ProdutoUseCaseOutboundPort outboundPort;
    private final PedidoUseCaseOutboundPort pedidoOutboundPort;

    public Boolean produtoExiste(Long id) {

        return outboundPort.produtoExiste(id);
    }

    @Override
    public Produto salvarInventario(Produto produto) throws NegocioException {

        produto.setProntaEntrega(true);
        return salvar(produto);
    }

    @Override
    public Produto salvar(Produto produto) throws NegocioException {

        var pedido = pedidoOutboundPort.buscarPeloId(produto.getPedido().getId());
        if(pedido != null && !outboundPort.produtoExiste(produto.getId())) {
            pedido.incrementarValor(produto.getValor());

            // if(produto.getValor() + pedido.getValorPago() > pedido.getValor())
            // throw new NegocioException("Valor pago não pode ser superior ao valor do pedido!");
            

            pedidoOutboundPort.salvar(pedido);
        }

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
