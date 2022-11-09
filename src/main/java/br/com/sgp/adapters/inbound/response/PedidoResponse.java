package br.com.sgp.adapters.inbound.response;

import br.com.sgp.application.core.domain.Aluno;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


@Getter
@Setter
public class PedidoResponse {
    private Long id;
    private OffsetDateTime data;
    private Integer valor;
    private String situacao;
    private String statusPagamento;
    private BigDecimal valorPago;
    private Long temporadaId;
    private Aluno aluno;
    private OffsetDateTime previsaoDeEntrega;
}