package br.com.sgp.adapters.inbound.entity;

import br.com.sgp.application.core.domain.TipoProduto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "TEMPORADA")
public class TemporadaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "DATA_INICIO")
    private OffsetDateTime dataInicio;

    @Column(name = "DATA_FIM")
    private OffsetDateTime dataFim;

    @MapKeyColumn
    @MapKeyEnumerated(EnumType.STRING)
    private HashMap<TipoProduto, Integer> catalogo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "temporada")
    List<PedidoEntity> pedidos;
}
