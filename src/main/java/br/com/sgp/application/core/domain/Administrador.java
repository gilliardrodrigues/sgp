package br.com.sgp.application.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Administrador {

    private Long id;
    private String username;
    private String password;
    private String nome;
    private String email;
}
