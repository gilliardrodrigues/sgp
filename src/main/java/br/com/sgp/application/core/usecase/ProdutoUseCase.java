package br.com.sgp.application.core.usecase;

import br.com.sgp.application.core.domain.*;
import br.com.sgp.application.core.exception.NegocioException;
import br.com.sgp.application.ports.in.ProdutoUseCaseInboundPort;
import br.com.sgp.application.ports.out.PedidoUseCaseOutboundPort;
import br.com.sgp.application.ports.out.ProdutoUseCaseOutboundPort;
import br.com.sgp.application.ports.out.TemporadaUseCaseOutboundPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
public class ProdutoUseCase implements ProdutoUseCaseInboundPort {

    private final ProdutoUseCaseOutboundPort outboundPort;
    private final PedidoUseCaseOutboundPort pedidoOutboundPort;
    private final TemporadaUseCaseOutboundPort temporadaOutboundPort;

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

        if(produto.getProntaEntrega() == null) {
            produto.setProntaEntrega(false);
        }
        if(!produto.getProntaEntrega()) {
            var tipo = produto.getTipo();
            var catalogo = temporadaOutboundPort.buscarAtiva().getCatalogo();
            produto.setValor(catalogo.get(tipo));
        }
        if(produto.getPedido() != null) {
            var pedido = pedidoOutboundPort.buscarPeloId(produto.getPedido().getId());
            if(pedido != null && !outboundPort.produtoExiste(produto.getId())) {
                pedido.incrementarValor(produto.getValor());
                // if(produto.getValor() + pedido.getValorPago() > pedido.getValor())
                // throw new NegocioException("Valor pago não pode ser superior ao valor do pedido!");
                pedidoOutboundPort.salvar(pedido);
            }
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

    public String converterListasDeProdutosParaJson(List<Camisa> camisas, List<Caneca> canecas, List<Tirante> tirantes) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String produtos = "";
        if(!camisas.isEmpty()) {
            var camisasJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(camisas);
            produtos = "[" + camisasJson.substring(1, camisasJson.length() - 1);
        }
        if(!canecas.isEmpty()) {
            var canecasJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(canecas);
            if(produtos.isBlank()) {
                produtos = "[" + canecasJson.substring(1, canecasJson.length() - 1);
            }
            else {
                produtos = produtos + "," + canecasJson.substring(1, canecasJson.length() - 1);
            }
        }
        if(!tirantes.isEmpty()) {
            var tirantesJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(tirantes);
            if(produtos.isBlank()) {
                produtos = "[" + tirantesJson.substring(1, tirantesJson.length() - 1);
            }
            else {
                produtos = produtos + "," + tirantesJson.substring(1, tirantesJson.length() - 1);
            }
        }
        if(!produtos.isBlank()) {
            produtos += "]";
            JSONArray jsonArray = new JSONArray(produtos);
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject produto = jsonArray.getJSONObject(i);
                var produtoMap = produto.toMap();
                var pedidoMap = (HashMap<String, Object>) produtoMap.get("pedido");
                if (pedidoMap != null) {
                    var id = pedidoMap.get("id");
                    produtoMap.remove("pedido");
                    produtoMap.put("pedido", id);
                    jsonArray.put(i, produtoMap);
                }
            }
            produtos = jsonArray.toString();
        }
        else {
            produtos = "[]";
        }
        return produtos;
    }

    @Override
    public String buscarTodos() throws JsonProcessingException {

        var camisas = buscarTodasCamisas();
        var canecas = buscarTodasCanecas();
        var tirantes = buscarTodosTirantes();

        return converterListasDeProdutosParaJson(camisas, canecas, tirantes);
    }

    @Override
    public String buscarInventario() throws JsonProcessingException {

        var camisas = outboundPort.buscarCamisasDoInventario();
        var canecas = outboundPort.buscarCanecasDoInventario();
        var tirantes = outboundPort.buscarTirantesDoInventario();

        return converterListasDeProdutosParaJson(camisas, canecas, tirantes);
    }

    @Override
    public String buscarProdutosPeloIdPedido(Long idPedido) throws JsonProcessingException {

        var camisas = outboundPort.buscarCamisasPeloIdPedido(idPedido);
        var canecas = outboundPort.buscarCanecasPeloIdPedido(idPedido);
        var tirantes = outboundPort.buscarTirantesPeloIdPedido(idPedido);

        return converterListasDeProdutosParaJson(camisas, canecas, tirantes);
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
    @Override
    public Produto adicionarProdutoDoInventarioAoPedido(Long idProduto, Long idPedido) {

        Produto produto;
        try {
            produto = outboundPort.buscarPeloId(idProduto);
            var pedido = pedidoOutboundPort.buscarPeloId(idPedido);
            produto.setPedido(pedido);
            return salvar(produto);
        } catch (Throwable e) {
            throw new NegocioException(e.getMessage());
        }
    }
}
