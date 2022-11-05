package br.com.sgp.application.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Pedido {

    public Pedido() {
        data = OffsetDateTime.now();
        valor = BigDecimal.ZERO;
        situacao = StatusPedido.AGUARDANDO_PAGAMENTO;
        statusPagamento = StatusPagamento.NAO_PAGO;
        valorPago = BigDecimal.ZERO;
        produtos = new ArrayList<Produto>();
    }

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
