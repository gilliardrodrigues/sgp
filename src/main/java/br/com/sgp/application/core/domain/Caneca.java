package br.com.sgp.application.core.domain;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Caneca extends Produto {

    private String modelo;

    public Caneca() {

        tipo = TipoProduto.CANECA;
    }

    public Caneca(Long id, Integer valor, Boolean entregue,
            Boolean prontaEntrega, Boolean chegou, TipoProduto tipo, Pedido pedido,
            OffsetDateTime previsaoDeEntrega, String modelo) {
        super(id, valor, entregue, prontaEntrega, chegou, tipo, previsaoDeEntrega, pedido);
        this.modelo = modelo;
        this.tipo = TipoProduto.CANECA;
    }

}
