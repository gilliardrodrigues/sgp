package br.com.sgp.adapters.inbound.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoRequest {

    protected int valor;
    protected Boolean entregue;
    protected Boolean chegou;
    //protected TipoProduto tipo;
    protected Long pedidoId;
}
