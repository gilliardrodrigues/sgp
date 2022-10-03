package br.com.sgp.application.core.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Camisa extends Produto {

    private Curso curso;
    private TamanhoCamisa tamanho;
    private CorCamisa cor;

    public Camisa() {
        tipo = TipoProduto.CAMISA;
    }
}
