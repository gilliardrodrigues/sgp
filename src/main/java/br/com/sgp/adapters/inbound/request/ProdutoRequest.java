package br.com.sgp.adapters.inbound.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoRequest {

    private Double valor;
    protected Boolean entregue;
    protected Boolean prontaEntrega;
    protected Boolean chegou;
    // protected Pedido pedido;
    // protected Fornecedor fornecedor;

}
