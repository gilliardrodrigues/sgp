package br.com.sgp.adapters.inbound.entity;

import br.com.sgp.application.core.domain.TipoProduto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

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
    protected BigDecimal valor;

    @Column(name = "ENTREGUE")
    protected Boolean entregue;

    @Column(name = "PRONTA_ENTREGA")
    protected Boolean prontaEntrega;

    @Column(name = "CHEGOU")
    protected Boolean chegou;

    @Enumerated(value = EnumType.STRING)
    protected TipoProduto tipo;

    @ManyToOne
    @JoinColumn(name = "pedidoId")
    protected PedidoEntity pedido;
}
