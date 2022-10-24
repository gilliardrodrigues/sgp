package br.com.sgp.adapters.inbound.entity;

import br.com.sgp.application.core.domain.TipoProduto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type", discriminatorType = DiscriminatorType.STRING)
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "VALOR")
    protected Double valor;

    @Column(name = "ENTREGUE")
    protected Boolean entregue;

    @Column(name = "PRONTA_ENTREGA")
    protected Boolean prontaEntrega;

    @Column(name = "CHEGOU")
    protected Boolean chegou;

    @Enumerated(value = EnumType.STRING)
    protected TipoProduto tipo;

    @ManyToOne
    @JoinColumn(name = "pedidoId", nullable = false)
    protected PedidoEntity pedido;

    @OneToMany
    protected List<FornecedorEntity> fornecedores;
}
