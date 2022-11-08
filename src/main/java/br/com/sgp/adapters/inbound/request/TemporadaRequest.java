package br.com.sgp.adapters.inbound.request;

import br.com.sgp.application.core.domain.TipoProduto;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class TemporadaRequest {

    private String descricao;
    private HashMap<TipoProduto, Integer> catalogo;
}
