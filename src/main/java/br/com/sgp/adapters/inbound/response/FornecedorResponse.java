package br.com.sgp.adapters.inbound.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FornecedorResponse {

    private Long id;
    private String razaoSocial;
    private String CNPJ;
    private String email;
    private Integer tempoEntregaEmDias;
    List<String> produtosOferecidos;
}
