package br.com.sgp.application.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class Temporada {

    private Long id;
    private String descricao;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
    private List<TipoProduto> produtosDisponiveis;
    private List<Pedido> pedidos;

    public void habilitarProduto(TipoProduto tipoDeProduto) {

        this.getProdutosDisponiveis().add(tipoDeProduto);
    }
}
