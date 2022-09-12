package br.com.sgp.application.core.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Fornecedor {

    private Long id;
    private String razaoSocial;
    private String CNPJ;
    private String email;
    private Integer tempoEntregaEmDias;
}
