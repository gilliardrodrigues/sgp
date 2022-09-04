package br.com.sgp.application.core.domain;

public enum StatusPedido {

    AGUARDANDO_PAGAMENTO("Aguardando pagamento..."),
    PEDIDO_FEITO("Pedido feito!"),
    CONCLUIDO("Concluído!");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
