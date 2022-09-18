package br.com.sgp.application.core.domain;

public enum StatusPedido {

    AGUARDANDO_PAGAMENTO("Aguardando pagamento..."),
    CONFIRMADO("Pedido confirmado!"),
    PARCIALMENTE_ENTREGUE("Pedido parcialmente entregue!"),
    PEDIDO_ENTREGUE("Pedido entregue!");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
