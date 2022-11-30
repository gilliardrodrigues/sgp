package br.com.sgp.application.core.domain;

import com.fasterxml.jackson.annotation.JsonValue;

import br.com.sgp.config.Generated;

@Generated
public enum Curso {

    CIENCIA_DA_COMPUTACAO("Ciência da Computação"),
    SISTEMAS_DE_INFORMACAO("Sistemas de Informação");

    private final String descricao;

    Curso(String descricao) {

        this.descricao = descricao;
    }

    @Generated
    public String getDescricao() {

        return descricao;
    }

    @Generated
    @JsonValue
    @Override
    public String toString() {

        return descricao;
    }
}
