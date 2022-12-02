package br.com.sgp.application.core.domain;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.Objects;

import br.com.sgp.config.Generated;

@Getter
@Setter
@ToString
@AllArgsConstructor
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
    private Integer valor;
    private StatusPedido situacao;
    private StatusPagamento statusPagamento;
    private Integer valorPago;
    private Temporada temporada;
    private Aluno aluno;
    private OffsetDateTime previsaoDeEntrega;

    public void incrementarValor(Integer incremento) {
        this.valor = incremento + this.valor;
    }

    public void setValorPago(Integer valorPago) {
        this.valorPago = valorPago;

        if (this.valorPago.equals(valor))
            this.statusPagamento = StatusPagamento.INTEGRALMENTE_PAGO;
        else if (this.valorPago > 0)
            this.statusPagamento = StatusPagamento.PARCIALMENTE_PAGO;
        else
            this.statusPagamento = StatusPagamento.NAO_PAGO;

        if (this.valorPago > 0)
            this.situacao = StatusPedido.CONFIRMADO;
        else
            this.situacao = StatusPedido.AGUARDANDO_PAGAMENTO;
    }

    public void addPrevisaoDeEntrega(OffsetDateTime novaData) {
        if (this.previsaoDeEntrega == null)
            this.previsaoDeEntrega = novaData;
        else
            this.previsaoDeEntrega = novaData.isAfter(this.previsaoDeEntrega) ? novaData : this.previsaoDeEntrega;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Pedido pedido = (Pedido) o;
        return id.equals(pedido.id);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
