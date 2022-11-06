package br.com.sgp.adapters.inbound.response;

import br.com.sgp.application.core.domain.TipoProduto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class TemporadaResponse {

    private Long id;
    private String descricao;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
    private List<String> produtos;
    private HashMap<TipoProduto, BigDecimal> valores;
}
