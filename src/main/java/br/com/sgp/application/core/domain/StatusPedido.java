package br.com.sgp.application.core.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusPedido {

    AGUARDANDO_PAGAMENTO("Aguardando pagamento..."),
    CONFIRMADO("Confirmado!"),
    PARCIALMENTE_PRONTO_PARA_ENTREGA("Alguns itens chegaram!"),
    PRONTO_PARA_ENTREGA("Pronto para entrega!"),
    PARCIALMENTE_ENTREGUE("Parcialmente entregue!"),
    ENTREGUE("Entregue!");

    private final String descricao;

    StatusPedido(String descricao) {

        this.descricao = descricao;
    }

    public String getDescricao() {

        return descricao;
    }
    @JsonValue
    @Override
    public String toString() {

        return descricao;
    }
}
