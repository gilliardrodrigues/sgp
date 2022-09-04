package br.com.sgp.application.core.domain;

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
}
