package br.com.sgp.application.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Camisa extends Produto {

    public Camisa(Curso curso, TamanhoCamisa tamanho, CorCamisa cor, Long id, Integer valor, Boolean entregue,
                        Boolean prontaEntrega, Boolean chegou, TipoProduto tipo, Pedido pedido,
                        OffsetDateTime previsaoDeEntrega) {
        super(id, valor, entregue, prontaEntrega, chegou, tipo, previsaoDeEntrega, pedido);
        this.cor = cor;
        this.curso = curso;
        this.tamanho = tamanho;
        this.tipo = TipoProduto.CAMISA;
    }

    private Curso curso;
    private TamanhoCamisa tamanho;
    private CorCamisa cor;

    public Camisa() {

        tipo = TipoProduto.CAMISA;
    }
}
