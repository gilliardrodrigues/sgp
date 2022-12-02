package br.com.sgp.adapters.inbound.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class AlunoEmbeddable {

    private String nome;
    private String email;
    private String telefone;
}
