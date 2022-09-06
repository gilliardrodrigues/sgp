package br.com.sgp.application.core.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Administrador extends Pessoa {

    private String username;
    private String password;
}
