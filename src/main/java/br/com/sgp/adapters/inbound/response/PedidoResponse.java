package br.com.sgp.adapters.inbound.response;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import br.com.sgp.application.core.domain.Aluno;
import br.com.sgp.application.core.domain.Produto;
import br.com.sgp.application.core.domain.StatusPagamento;
import br.com.sgp.application.core.domain.StatusPedido;
import br.com.sgp.application.core.domain.Temporada;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PedidoResponse {
    private Long id;
    private OffsetDateTime data;
    private BigDecimal valor;
    private StatusPedido situacao;
    private StatusPagamento statusPagamento;
    private BigDecimal valorPago;
    private Temporada temporada;
    private Aluno aluno;
    private List<Produto> produtos;
}