package br.com.sgp.application.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.HashMap;

@Getter
@Setter
public class Temporada {

    private Long id;
    private String descricao;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
    private HashMap<TipoProduto, Integer> catalogo;

    public void habilitarProduto(TipoProduto tipoDeProduto, Integer valor) {

        this.catalogo.put(tipoDeProduto, valor);
    }
}
