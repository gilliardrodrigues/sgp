package br.com.sgp.application.core.domain;

public enum TipoProduto {

    CAMISA("Camisa"),
    CANECA("Caneca"),
    TIRANTE("Tirante");

    private final String descricao;

    TipoProduto(String descricao) {

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
