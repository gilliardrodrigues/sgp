package br.com.sgp.application.core.domain;

public enum Curso {

    CIENCIA_DA_COMPUTACAO("CIÊNCIA DA COMPUTAÇÃO"),
    SISTEMAS_DE_INFORMACAO("SISTEMAS DE INFORMAÇÃO");

    private final String descricao;

    Curso(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
