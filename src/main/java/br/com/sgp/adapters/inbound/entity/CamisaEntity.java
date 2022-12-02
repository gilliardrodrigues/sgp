package br.com.sgp.adapters.inbound.entity;

import br.com.sgp.application.core.domain.CorCamisa;
import br.com.sgp.application.core.domain.Curso;
import br.com.sgp.application.core.domain.TamanhoCamisa;
import br.com.sgp.application.core.domain.TipoProduto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = "CAMISA")
@AllArgsConstructor
@NoArgsConstructor
public class CamisaEntity extends ProdutoEntity {

    public CamisaEntity(Curso curso, TamanhoCamisa tamanho, CorCamisa cor, Long id, Integer valor, Boolean entregue,
                 Boolean prontaEntrega, Boolean chegou, TipoProduto tipo, PedidoEntity pedido,
                 OffsetDateTime previsaoDeEntrega) {
        super(id, valor, entregue, prontaEntrega, chegou, tipo, pedido, previsaoDeEntrega);
        this.cor = cor;
        this.curso = curso;
        this.tamanho = tamanho;
    }

    @Column(name = "CURSO")
    @Enumerated(value = EnumType.STRING)
    private Curso curso;

    @Column(name = "TAMANHO_CAMISA")
    @Enumerated(value = EnumType.STRING)
    private TamanhoCamisa tamanho;

    @Column(name = "COR_CAMISA")
    @Enumerated(value = EnumType.STRING)
    private CorCamisa cor;

}
