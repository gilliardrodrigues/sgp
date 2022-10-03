package br.com.sgp.application.core.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Caneca extends Produto {

    private String modelo;

    public Caneca() {
        tipo = TipoProduto.CANECA;
    }

}
