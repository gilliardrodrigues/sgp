package br.com.sgp.adapters.inbound.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoRequest {

    protected BigDecimal valor;
    protected Boolean entregue;
    protected Boolean chegou;

}
