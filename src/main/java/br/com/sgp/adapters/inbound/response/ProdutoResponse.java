package br.com.sgp.adapters.inbound.response;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class ProdutoResponse {

    private Long id;
    private Integer valor;
    private Boolean entregue;
    private Boolean prontaEntrega;
    private Boolean chegou;
    private String tipo;
    private Long pedidoId;
    private OffsetDateTime previsaoDeEntrega;
}
