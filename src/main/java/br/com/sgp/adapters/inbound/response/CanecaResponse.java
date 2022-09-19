package br.com.sgp.adapters.inbound.response;

import br.com.sgp.application.core.domain.Fornecedor;
import br.com.sgp.application.core.domain.Pedido;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CanecaResponse {
    private Long id;
    private String nome;
    private Double valor;
    private Boolean entregue;
    private Boolean prontaEntrega;
    private Boolean chegou;
    private Pedido pedido;
    private Fornecedor fornecedor;
    private String modelo;

}
