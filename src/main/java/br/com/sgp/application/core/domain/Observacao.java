package br.com.sgp.application.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Observacao {

    private Long id;
    private String comentario;
    private OffsetDateTime data;
    private Fornecedor fornecedor;
    //private Administrador autor;
}
