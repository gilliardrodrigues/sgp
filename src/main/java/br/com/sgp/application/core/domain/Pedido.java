package br.com.sgp.application.core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.Objects;

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
        this.valor = incremento + this.valor;

        if (this.valor == 0)
            return;

    }

    public void setValorPago(int valorPago) {
        this.valorPago = valorPago;

        if (this.valorPago == valor)
            this.statusPagamento = StatusPagamento.INTEGRALMENTE_PAGO;
        else if (this.valorPago > 0)
            this.statusPagamento = StatusPagamento.PARCIALMENTE_PAGO;
        else
            this.statusPagamento = StatusPagamento.NAO_PAGO;

        if(this.valorPago > 0) this.situacao = StatusPedido.CONFIRMADO;
        else this.situacao = StatusPedido.AGUARDANDO_PAGAMENTO;
    }

    public void addPrevisaoDeEntrega(OffsetDateTime previsaoDeEntrega) {
        if (this.previsaoDeEntrega == null)
            this.previsaoDeEntrega = previsaoDeEntrega;
        else
            this.previsaoDeEntrega = previsaoDeEntrega.isAfter(this.previsaoDeEntrega) ? previsaoDeEntrega
                    : this.previsaoDeEntrega;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return id.equals(pedido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
