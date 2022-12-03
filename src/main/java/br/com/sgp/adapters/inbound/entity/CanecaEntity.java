package br.com.sgp.adapters.inbound.entity;

import br.com.sgp.application.core.domain.TipoProduto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = "CANECA")
@AllArgsConstructor
@NoArgsConstructor
public class CanecaEntity extends ProdutoEntity {

    public CanecaEntity(String modelo, Long id, Integer valor, Boolean entregue, Boolean prontaEntrega,
            Boolean chegou, TipoProduto tipo, PedidoEntity pedido, OffsetDateTime previsaoDeEntrega) {

        super(id, valor, entregue, prontaEntrega, chegou, tipo, pedido, previsaoDeEntrega);
        this.modelo = modelo;
    }

    @Column(name = "MODELO_CANECA")
    private String modelo;
}
