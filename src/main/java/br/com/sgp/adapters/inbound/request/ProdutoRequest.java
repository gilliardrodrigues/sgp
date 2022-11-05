package br.com.sgp.adapters.inbound.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import br.com.sgp.application.core.domain.Pedido;

@Getter
@Setter
public class ProdutoRequest {

    protected BigDecimal valor;
    protected Boolean entregue;
    protected Boolean chegou;
    protected Pedido pedido;
}
