package br.com.sgp.application.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Produto {

    protected Long id;
    protected BigDecimal valor;
    protected Boolean entregue;
    protected Boolean prontaEntrega;
    protected Boolean chegou;
    protected Pedido pedido;
    protected TipoProduto tipo;
}
