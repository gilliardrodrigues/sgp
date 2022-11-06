package br.com.sgp.application.core.domain;

public enum StatusPedido {

    AGUARDANDO_PAGAMENTO("Aguardando pagamento..."),
    CONFIRMADO("Confirmado!"),
    PARCIALMENTE_ENTREGUE("Parcialmente entregue!"),
    PEDIDO_ENTREGUE("Entregue!");

    private final String descricao;

    StatusPedido(String descricao) {

        this.descricao = descricao;
    }

    public String getDescricao() {

        return descricao;
    }

    @Override
    public String toString() {

        return descricao;
    }
}
