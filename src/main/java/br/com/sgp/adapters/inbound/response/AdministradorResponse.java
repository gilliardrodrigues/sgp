package br.com.sgp.adapters.inbound.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministradorResponse {

    private Long id;
    private String username;
    private String nome;
    private String email;
    private String telefone;
}
