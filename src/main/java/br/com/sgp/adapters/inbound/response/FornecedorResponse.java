package br.com.sgp.adapters.inbound.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FornecedorResponse {

    private Long id;
    private String razaoSocial;
    private String CNPJ;
    private String email;
    private Integer tempoEntregaEmDias;
}
