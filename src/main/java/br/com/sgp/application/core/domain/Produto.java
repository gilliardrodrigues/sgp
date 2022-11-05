package br.com.sgp.application.core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@ToString
public class Produto {

    protected Long id;
    protected BigDecimal valor;
    protected Boolean entregue;
    protected Boolean prontaEntrega;
    protected Boolean chegou;
    protected TipoProduto tipo;
    protected OffsetDateTime previsaoDeEntrega;
    // protected Fornecedor fornecedor;
    protected Pedido pedido;

    // public Integer calcularDiasParaEntrega() {
    //     if(this.dataDaEncomenda == null) return 0;
        
    //     Integer diasAposEncomenda = (int) ChronoUnit.DAYS.between(OffsetDateTime.now(), dataDaEncomenda);

    //     return Math.min(this.fornecedor.getTempoEntregaEmDias() - diasAposEncomenda, 0);
    // }
}
