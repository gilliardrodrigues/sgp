package br.com.sgp.adapters.inbound.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CamisaResponse extends ProdutoResponse {

    private String curso;
    private String tamanho;
    private String cor;
}
