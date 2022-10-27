package br.com.sgp.application.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class Observacao {

    private Long id;
    private String comentario;
    private OffsetDateTime data;
    private Fornecedor fornecedor;
    private Administrador autor;
}
