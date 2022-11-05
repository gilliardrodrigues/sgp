package br.com.sgp.adapters.inbound.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Embeddable
public class AlunoEmbeddable {

    private String nome;
    private String email;
    private String telefone;
}
