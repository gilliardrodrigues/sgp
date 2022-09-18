package br.com.sgp.adapters.inbound.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ObservacaoRequest {

    @NotBlank(message = "Campo obrigatório não preenchido!")
    private String comentario;
}
