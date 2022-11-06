package br.com.sgp.application.core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Pedido {

    public Pedido() {
        data = OffsetDateTime.now();
        valor = 0;
        situacao = StatusPedido.AGUARDANDO_PAGAMENTO;
        statusPagamento = StatusPagamento.NAO_PAGO;
        valorPago = 0;
        // produtos = new ArrayList<Produto>();
    }

    private Long id;
    private OffsetDateTime data;
    private int valor;
    private StatusPedido situacao;
    private StatusPagamento statusPagamento;
    private int valorPago;
    private Temporada temporada;
    private Aluno aluno;
    protected OffsetDateTime previsaoDeEntrega;

    public void incrementarValor(int incremento) {
        this.valorPago = valor;

        if(this.valorPago == 0) return;

        if(this.valorPago + incremento >= this.valor)  {
            this.valorPago = this.valor;
            this.statusPagamento = StatusPagamento.INTEGRALMENTE_PAGO;
        } else
            this.statusPagamento = StatusPagamento.PARCIALMENTE_PAGO;

        this.situacao = StatusPedido.CONFIRMADO;
    }

    public void addPrevisaoDeEntrega(OffsetDateTime previsaoDeEntrega) {
        if(this.previsaoDeEntrega == null) 
            this.previsaoDeEntrega = previsaoDeEntrega;
        else
            this.previsaoDeEntrega = previsaoDeEntrega.isAfter(this.previsaoDeEntrega) ? previsaoDeEntrega : this.previsaoDeEntrega;
    }
}
