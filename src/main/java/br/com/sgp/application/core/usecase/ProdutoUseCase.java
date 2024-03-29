package br.com.sgp.application.core.usecase;

import br.com.sgp.application.core.domain.*;
import br.com.sgp.application.core.exception.EntidadeNaoEncontradaException;
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
        produto.setChegou(true);
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
            if(produto.getChegou() && pedido.getSituacao().equals(StatusPedido.CONFIRMADO)) {
                pedido.setSituacao(StatusPedido.PARCIALMENTE_PRONTO_PARA_ENTREGA);
            }
            if(produto.getEntregue()) {
                pedido.setSituacao(StatusPedido.PARCIALMENTE_ENTREGUE);
            }
            if(pedido != null && !outboundPort.produtoExiste(produto.getId())) {
                pedido.incrementarValor(produto.getValor());
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
    
    @Override
    public Camisa alterarCamisa(Camisa camisaRequest) {

        Camisa camisaSalva;
        try {
            camisaSalva = (Camisa) outboundPort.buscarPeloId(camisaRequest.getId());
            camisaSalva.setCor(camisaRequest.getCor());
            camisaSalva.setCurso(camisaRequest.getCurso());
            camisaSalva.setTamanho(camisaRequest.getTamanho());
            camisaSalva.setValor(camisaRequest.getValor());
        } catch (Throwable e) {
            throw new EntidadeNaoEncontradaException(e.getMessage());
        }
        return outboundPort.salvarCamisa(camisaSalva);
    }

    @Override
    public Caneca alterarCaneca(Caneca canecaRequest) {

        Caneca canecaSalva;
        try {
            canecaSalva = (Caneca) outboundPort.buscarPeloId(canecaRequest.getId());
            canecaSalva.setModelo(canecaRequest.getModelo());
            canecaSalva.setValor(canecaRequest.getValor());
        } catch (Throwable e) {
            throw new EntidadeNaoEncontradaException(e.getMessage());
        }
        return outboundPort.salvarCaneca(canecaSalva);
    }

    @Override
    public Tirante alterarTirante(Tirante tiranteRequest) {

        Tirante tiranteSalvo;
        try {
            tiranteSalvo = (Tirante) outboundPort.buscarPeloId(tiranteRequest.getId());
            tiranteSalvo.setModelo(tiranteRequest.getModelo());
            tiranteSalvo.setValor(tiranteRequest.getValor());
        } catch (Throwable e) {
            throw new EntidadeNaoEncontradaException(e.getMessage());
        }
        return outboundPort.salvarTirante(tiranteSalvo);
    }

    @Override
    public void marcarChegadaTipoDeProduto(TipoProduto tipoProduto) {

        if(!temporadaOutboundPort.existeTemporadaAtiva()) {
            if(tipoProduto.equals(TipoProduto.CAMISA)) {
                var camisas = outboundPort.buscarTodasCamisas();
                camisas.forEach(camisa -> {
                    camisa.setChegou(true);
                    outboundPort.salvarCamisa(camisa);
                });
            }
            else if(tipoProduto.equals(TipoProduto.CANECA)) {
                var canecas = outboundPort.buscarTodasCanecas();
                canecas.forEach(caneca -> {
                    caneca.setChegou(true);
                    outboundPort.salvarCaneca(caneca);
                });
            }
            else {
                var tirantes = outboundPort.buscarTodosTirantes();
                tirantes.forEach(tirante -> {
                    tirante.setChegou(true);
                    outboundPort.salvarTirante(tirante);
                });
            }
        }
        else {
            throw new NegocioException("Não é possível prosseguir porque a temporada ainda está em andamento!");
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

        Produto produto;
        try {
            produto = buscarPeloId(id);
        } catch (Throwable e) {
            throw new EntidadeNaoEncontradaException(e.getMessage());
        }
        if(produto.getPedido() != null) {
            var pedido = produto.getPedido();
            if(!pedido.getStatusPagamento().equals(StatusPagamento.NAO_PAGO)) {
                throw new NegocioException("Não foi possível excluir o produto, pois ele está associado a um pedido pago!");
            }
        }
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
            pedido.incrementarValor(produto.getValor());
            pedidoOutboundPort.salvar(pedido);
            return salvar(produto);
        } catch (Throwable e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Override
    public Produto desassociarProdutoDoPedido(Long idProduto, Long idPedido) {

        Produto produto;
        try {
            produto = outboundPort.buscarPeloId(idProduto);
            var pedido = pedidoOutboundPort.buscarPeloId(idPedido);
            produto.setPedido(null);
            pedido.setValor(pedido.getValor() - produto.getValor());
            pedidoOutboundPort.salvar(pedido);
            return salvar(produto);
        } catch (Throwable e) {
            throw new NegocioException(e.getMessage());
        }
    }
}
