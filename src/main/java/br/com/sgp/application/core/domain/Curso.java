package br.com.sgp.application.core.domain;

import com.fasterxml.jackson.annotation.JsonValue;

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

    @JsonValue
    @Override
    public String toString() {

        return descricao;
    }
}
