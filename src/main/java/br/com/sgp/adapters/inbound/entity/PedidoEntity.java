package br.com.sgp.adapters.inbound.entity;

import br.com.sgp.application.core.domain.StatusPagamento;
import br.com.sgp.application.core.domain.StatusPedido;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "PEDIDO")
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "DATA")
    private OffsetDateTime data;

    @Column(name = "VALOR")
    private Double valor;

    @Column(name = "SITUACAO")
    @Enumerated(value = EnumType.STRING)
    private StatusPedido situacao;

    @Column(name = "STATUS_PAGAMENTO")
    @Enumerated(value = EnumType.STRING)
    private StatusPagamento statusPagamento;

    @Column(name = "VALOR_PAGO")
    private Double valorPago;

    @ManyToOne
    @JoinColumn(name = "temporadaId")
    private TemporadaEntity temporada;

    //private AlunoEntity aluno;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido")
    private List<ProdutoEntity> produtos;

}
