package br.com.sgp.application.core.domain;

public enum TipoProduto {

    CAMISA("CAMISA"),
    CANECA("CANECA"),
    TIRANTE("TIRANTE");

    private final String descricao;

    TipoProduto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
