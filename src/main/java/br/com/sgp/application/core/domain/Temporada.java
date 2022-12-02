package br.com.sgp.application.core.domain;

import br.com.sgp.adapters.inbound.entity.PedidoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Temporada {

    private Long id;
    private String descricao;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
    private HashMap<TipoProduto, Integer> catalogo;
    private List<PedidoEntity> pedidos;

    public void habilitarProduto(TipoProduto tipoDeProduto, Integer valor) {

        this.catalogo.put(tipoDeProduto, valor);
    }
}
