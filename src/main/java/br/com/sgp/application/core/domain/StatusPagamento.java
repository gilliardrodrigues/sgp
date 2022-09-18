package br.com.sgp.application.core.domain;

public enum StatusPagamento {

    INTEGRALMENTE_PAGO("Pago"),
    PARCIALMENTE_PAGO("Parcialmente pago"),
    NAO_PAGO("Não pago");

    private final String descricao;

    StatusPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
