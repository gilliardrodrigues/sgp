package br.com.sgp.adapters.inbound.entity;

import br.com.sgp.application.core.domain.TipoProduto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
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

    @ElementCollection(targetClass = TipoProduto.class)
    @JoinTable(name = "TEMPORADA_PRODUTO_DISPONIVEL", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "PRODUTO_DISPONIVEL", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<TipoProduto> produtosDisponiveis;

    @MapKeyColumn
    @MapKeyEnumerated(EnumType.STRING)
    private HashMap<TipoProduto, BigDecimal> valores;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "temporada")
    List<PedidoEntity> pedidos;
}
