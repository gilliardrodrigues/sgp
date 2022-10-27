package br.com.sgp.adapters.inbound.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoRequest {

    protected Double valor;
    protected Boolean entregue;
    protected Boolean chegou;

}
