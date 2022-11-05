package br.com.sgp.application.core.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Administrador {

    private Long id;
    private String username;
    private String password;
    private String nome;
    private String email;
}
