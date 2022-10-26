package br.com.sgp.application.core.domain;

public enum CorCamisa {

    PRETO("PRETO"),
    BRANCO("BRANCO"),
    AZUL("AZUL"),
    VINHO("VINHO"),
    CINZA("CINZA"),
    ROSA("ROSA"),
    PETROLEO("PETRÓLEO"),
    VERDE("VERDE");
    private final String descricao;

    CorCamisa(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
