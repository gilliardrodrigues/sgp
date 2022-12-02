package br.com.sgp.application.core.domain;

import lombok.*;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Produto {

    protected Long id;
    protected Integer valor;
    protected Boolean entregue;
    protected Boolean prontaEntrega;
    protected Boolean chegou;
    protected TipoProduto tipo;
    protected OffsetDateTime previsaoDeEntrega;
    protected Pedido pedido;

    public OffsetDateTime calcularPrevisaoDeEntrega (List<Fornecedor> fornecedores) {
        for(Fornecedor fornecedor : fornecedores) {
            for(TipoProduto tipoProduto : fornecedor.getProdutosOferecidos()) {
                if(tipoProduto.equals(this.tipo)) {
                    return OffsetDateTime.now().plus(fornecedor.getTempoEntregaEmDias(), ChronoUnit.DAYS);
                }
            }
        }

        return null;
    }
}
