package br.com.sgp.application.core.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CorCamisa {

    PRETO("Preto"),
    BRANCO("Branco"),
    AZUL("Azul"),
    VINHO("Vinho"),
    CINZA("Cinza"),
    ROSA("Rosa"),
    PETROLEO("Petróleo"),
    VERDE("Verde");
    private final String descricao;

    CorCamisa(String descricao) {

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
