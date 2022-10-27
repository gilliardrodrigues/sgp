package br.com.sgp.application.core.domain;

public enum StatusPedido {

    AGUARDANDO_PAGAMENTO("AGUARDANDO PAGAMENTO"),
    CONFIRMADO("CONFIRMADO"),
    PARCIALMENTE_ENTREGUE("PARCIALMENTE ENTREGUE"),
    PEDIDO_ENTREGUE("ENTREGUE");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
