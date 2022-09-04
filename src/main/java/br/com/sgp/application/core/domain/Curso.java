package br.com.sgp.application.core.domain;

public enum Curso {

    CIENCIA_DA_COMPUTACAO("Ciência da Computação"),
    SISTEMAS_DE_INFORMACAO("Sistemas de Informação");

    private final String descricao;

    Curso(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
