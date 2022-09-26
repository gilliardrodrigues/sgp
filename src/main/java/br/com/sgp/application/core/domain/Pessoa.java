package br.com.sgp.application.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pessoa {

    protected Long id;
    protected String nome;
    protected String email;
    protected String telefone;
}
