package br.com.sgp.adapters.inbound.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoRequest {

    protected Integer valor;
    protected Boolean entregue;
    protected Boolean chegou;
    protected Long pedidoId;
}
