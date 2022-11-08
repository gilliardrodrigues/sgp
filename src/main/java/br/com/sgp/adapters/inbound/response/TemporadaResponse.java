package br.com.sgp.adapters.inbound.response;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.HashMap;

@Getter
@Setter
public class TemporadaResponse {

    private Long id;
    private String descricao;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
    private HashMap<String, Integer> catalogo;
}
