package br.com.sgp.application.core.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Produto {

    protected Long id;
    protected Double valor;
    protected Boolean entregue;
    protected Boolean prontaEntrega;
    protected Boolean chegou;
    protected Pedido pedido;
    protected Fornecedor fornecedor;
    protected TipoProduto tipo;
}
