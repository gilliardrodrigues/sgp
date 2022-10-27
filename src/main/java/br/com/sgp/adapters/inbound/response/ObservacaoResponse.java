package br.com.sgp.adapters.inbound.response;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class ObservacaoResponse {

    private Long id;
    private String comentario;
    private OffsetDateTime data;
    private Long fornecedorId;
    //private Long administradorId;
}
