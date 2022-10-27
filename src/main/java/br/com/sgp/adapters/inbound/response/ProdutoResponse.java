package br.com.sgp.adapters.inbound.response;

import br.com.sgp.application.core.domain.TipoProduto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoResponse {

    private Long id;
    private Double valor;
    private Boolean entregue;
    private Boolean prontaEntrega;
    private Boolean chegou;
    private TipoProduto tipo;

    //private Long pedidoId;
}
