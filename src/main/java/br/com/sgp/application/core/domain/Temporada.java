package br.com.sgp.application.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class Temporada {

    private Long id;
    private String descricao;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
    private List<TipoProduto> produtosDisponiveis;
    private HashMap<TipoProduto, BigDecimal> valores;
    // private List<Pedido> pedidos; Gill, acredito que temporada não precisa ter o array de pedidos, basta fazermos a busca nos pedidos pela temporada

    public void habilitarProduto(TipoProduto tipoDeProduto) {

        this.getProdutosDisponiveis().add(tipoDeProduto);
    }
}
