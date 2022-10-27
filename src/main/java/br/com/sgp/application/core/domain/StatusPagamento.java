package br.com.sgp.application.core.domain;

public enum StatusPagamento {

    INTEGRALMENTE_PAGO("PAGO"),
    PARCIALMENTE_PAGO("PARCIALMENTE PAGO"),
    NAO_PAGO("NÃO PAGO");

    private final String descricao;

    StatusPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
