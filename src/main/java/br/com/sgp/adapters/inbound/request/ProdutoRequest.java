package br.com.sgp.adapters.inbound.request;

import br.com.sgp.application.core.domain.TipoProduto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoRequest {

    protected int valor;
    protected Boolean entregue;
    protected Boolean chegou;
    protected TipoProduto tipo;
    protected Long pedidoId;
}
