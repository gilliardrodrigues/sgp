package br.com.sgp.application.core.domain;

public enum TamanhoCamisa {

    PP("PP"),
    PPBL("PPBL"),
    P("P"),
    PBL("PBL"),
    M("M"),
    MBL("MBL"),
    G("G"),
    GBL("GBL"),
    GG("GG"),
    GGBL("GGBL"),
    XG("XG"),
    XGBL("XGBL");
    private final String descricao;

    TamanhoCamisa(String descricao) {

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
