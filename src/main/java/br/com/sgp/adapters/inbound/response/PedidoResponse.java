package br.com.sgp.adapters.inbound.response;

import br.com.sgp.application.core.domain.Aluno;
import br.com.sgp.application.core.domain.StatusPagamento;
import br.com.sgp.application.core.domain.StatusPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;


@Getter
@Setter
public class PedidoResponse {
    private Long id;
    private OffsetDateTime data;
    private int valor;
    private StatusPedido situacao;
    private StatusPagamento statusPagamento;
    private BigDecimal valorPago;
    private Long temporadaId;
    private Aluno aluno;
    private OffsetDateTime previsaoDeEntrega;
}